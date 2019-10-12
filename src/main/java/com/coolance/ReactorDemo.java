package com.coolance;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;

/**
 * @author Coolance
 */
public class ReactorDemo {

	public static void main(String[] args) {
		//reactor = jdk8 stream + jdk9 reactive stream
		//Mono 0-1个元素
		//Flux 0-N个元素
		String[] strs = {"1", "2", "3"};
		
		Subscriber<Integer> subscriber = new Subscriber<Integer>() {
			private Subscription subscription;


			@Override
			public void onSubscribe(Subscription subscription) {
				this.subscription = subscription;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("接受到的数据：" + item);
				this.subscription.request(1);
				
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
				this.subscription.cancel();
			}

			@Override
			public void onComplete() {
				System.out.println("处理完毕");
			}
			
		};
		
		Flux.fromArray(strs).map(s -> Integer.parseInt(s))
		.subscribe(subscriber);
	}

}
