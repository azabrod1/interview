package interview;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Population {
	private final long init_pop; 
	private final ThreadLocalRandom r;
	private final double mortality;
	private final double birthRate;
	private       long curr_pop;
	private 	  long T = 0;
	private final static int THREADS = 4;
	private final ArrayList<Long> history;
	private static final int interval = 100;

	
	public Population(long _init_pop, double _mortality, double _birthrate){
		this.curr_pop = this.init_pop = _init_pop; 
		this.r  	  = ThreadLocalRandom.current();
		this.mortality= _mortality;
		this.birthRate= _birthrate;
		this.history = new ArrayList<Long>();
	}
	
	public Population(long _init_pop, double _mortality){
		this(_init_pop, _mortality, 0);
	}
	
	
	public long run(long time){
		
		curr_pop = advance(time, curr_pop);
		T += time;

		return curr_pop;
	}
	
	public void live_show(){
		AtomicBoolean stop = new AtomicBoolean(false);
		Thread waitForUser = new Thread(new Reader(stop));
		waitForUser.start();
		
		while(!stop.get()){
			para_run(interval);
			printHistory((int)T-interval, (int) T);
		}
		
		System.out.printf("Live Show ended at %d\n", T);
		try{
		waitForUser.join();
		}catch (Exception e) {}
		
	}
	
	public long para_run(long time){
		
		Thread[]   threads = new Thread[THREADS];
		long[][]   results = new long[THREADS][(int) time];
		
		for(int th =0; th < THREADS-1; ++th){
			threads[th] = new Thread(new Worker(time, curr_pop/THREADS, results[th]));
		}
		
		threads[THREADS-1]  = new Thread(new Worker(time, curr_pop - ((THREADS-1)*(curr_pop/THREADS)), results[THREADS-1]));
		
		for(Thread thd: threads){
			thd.start();
		}
		for(Thread thd: threads){
			try {
				thd.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long sum;
		for(int curr = 0; curr < time; ++curr){
			sum = 0;
			for(int thd = 0; thd < THREADS; ++thd)
				sum += results[thd][curr];
			history.add(sum);
		}

		curr_pop = history.get(history.size()-1);
		T += time;
		return curr_pop;
		
	}
	
	public void printHistory(){
		
		for(int time = 0; time < history.size(); ++time){
			System.out.printf("%d.  %d\n", time, history.get(time));
		}
	
	}
	
public void printHistory(int from, int to){
		
		for(int time = from; time < to; ++time){
			System.out.printf("%d.  %d\n", time, history.get(time));
		}
	
	}
	
	
	
	
	private class Worker implements Runnable{
		final long t; final long pop; final long[] result; 
		public Worker(long _t, long _pop, long[] _result){
			this.t= _t; this.pop = _pop; this.result = _result;
		}
		@Override
		public void run() {
			thread_advance(t, pop, result);
		}
		
	}
	
	public void getStats(){
		
		System.out.println("Initial population: " + init_pop);
		System.out.println("Current population: " + curr_pop);
		System.out.printf("Percent of original: %.5f\n",100  * ((double) curr_pop/init_pop));
		System.out.println("Time period: "+ T);

	}
	
	
	private long advance(long t, long p){
		
		long p_last = p;

		while(t-- > 0 && p_last > 0){
			p = 0;
			while(p_last-- > 0){
				if(r.nextDouble() >= mortality) ++p;

			}
			p_last  = p;

		}

		return p;

	}
	
	private void thread_advance(long t, long p, long[] results){
		ThreadLocalRandom  rand = ThreadLocalRandom.current();
		long p_last = p; int x = 0;

		while(t-- > 0 && p_last > 0){
			p = 0;
			while(p_last-- > 0){
				if(rand.nextDouble() >= mortality) ++p;
				if(rand.nextDouble() <= birthRate) ++p;

			}
			results[x++] = p;
			p_last       = p;

		}

		//result.addAndGet(p_last);
	}
	
	private class Reader implements Runnable {
		private final Scanner io = new Scanner(System.in);
		private AtomicBoolean keyPressed;
		
		public Reader(AtomicBoolean _keyPressed){
			this.keyPressed = _keyPressed;
		}
		
		public void run() {
			io.nextLine();
			keyPressed.set(true);
		}
	}
	
	
	
	
	
	
	
	
	
}
