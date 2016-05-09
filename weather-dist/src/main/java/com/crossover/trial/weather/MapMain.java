package com.crossover.trial.weather;

import java.util.HashMap;
import java.util.Map;

public class MapMain {
	public static void main(String[] args) {
		/*for (Map.Entry<Double, Integer> e : radiusFreq.entrySet()) {
            int i = e.getKey().intValue() % 10;
            hist[i] += e.getValue();
        }*/
		
		Map<Double, Integer> map = new HashMap<>();
		map.put(1.0D, 1);
		map.put(10.2D, 2);
		map.put(20.3D, 3);
		map.put(20.4D, 4);
		
		//map.entrySet().stream().map((k,v)->k.intValue())
		
		map.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v));
	}
}
