package com.jointthinker.framework.common.util;

import java.util.LinkedList;

public class ReaderWriterLock {
	
	private int active_readers;     // = 0
    private int waiting_readers;    // = 0
    private int active_writers;     // = 0

    private final LinkedList writer_locks = new LinkedList();
    
	public synchronized void request_read(){
		if( active_writers==0 && writer_locks.size()==0 )
            ++active_readers;
        else
        {   ++waiting_readers;
            try{ wait(); }catch(InterruptedException e){}
        }
	}
	
	public void request_write(){
		Object lock = new Object();
        synchronized( lock )
        {   synchronized( this )
            {   boolean okay_to_write = writer_locks.size()==0 
                                        && active_readers==0
                                        && active_writers==0;
                if( okay_to_write )
                {   ++active_writers;
                    return; // the "return" jumps over the "wait" call
                }

                writer_locks.addLast( lock );
            }
            try{ lock.wait(); } catch(InterruptedException e){}
        }
	}
	
	public synchronized void read_accomplished(){
		if( --active_readers == 0 )
            notify_writers();
	}
	
	public void write_accomplished(){
		--active_writers;
        if( waiting_readers > 0 )   // priority to waiting readers
            notify_readers();
        else
            notify_writers();
	}
	
	private void notify_readers()       // must be accessed from a
    {                                   //  synchronized method
        active_readers  += waiting_readers;
        waiting_readers = 0;
        notifyAll();
    }
	
	private void notify_writers()       // must be accessed from a
    {                                   //  synchronized method
        if( writer_locks.size() > 0 )
        {   
            Object oldest = writer_locks.removeFirst();
            ++active_writers;
            synchronized( oldest ){ oldest.notify(); }
        }
    }
}
