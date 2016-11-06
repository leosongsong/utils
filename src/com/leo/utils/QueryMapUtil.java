package com.leo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Joiner;


public class QueryMapUtil {

	private static String urlDecode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Error in urlDecode.", e);
		}
	}
	
	/**
	 *  it is possible to have multiple query parameters with the same name
	 * @param s
	 * @return
	 */
	public static Map<String, String[]> parseQuery(String s) {
		if (s == null)
			return new HashMap<String, String[]>(0);
		// In map1 we use strings and ArrayLists to collect the parameter
		// values.
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		int p = 0;
		while (p < s.length()) {
			int p0 = p;
			while (p < s.length() && s.charAt(p) != '=' && s.charAt(p) != '&')
				p++;
			String name = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '=')
				p++;
			p0 = p;
			while (p < s.length() && s.charAt(p) != '&')
				p++;
			String value = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '&')
				p++;
			Object x = map1.get(name);
			if (x == null) {
				// The first value of each name is added directly as a string to
				// the map.
				map1.put(name, value);
			} else if (x instanceof String) {
				// For multiple values, we use an ArrayList.
				ArrayList<String> a = new ArrayList<String>();
				a.add((String) x);
				a.add(value);
				map1.put(name, a);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				a.add(value);
			}
		}
		// Copy map1 to map2. Map2 uses string arrays to store the parameter
		// values.
		HashMap<String, String[]> map2 = new HashMap<String, String[]>(
				map1.size());
		for (Map.Entry<String, Object> e : map1.entrySet()) {
			String name = e.getKey();
			Object x = e.getValue();
			String[] v;
			if (x instanceof String) {
				v = new String[] { (String) x };
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				v = new String[a.size()];
				v = a.toArray(v);
			}
			map2.put(name, v);
		}
		
		return map2;
	}

	
	/**
	 *  it is not possible to have multiple query parameters with the same name
	 *  don't support duplicate values 
	 * @param s
	 * @return
	 */
	public static Map<String, String> parseQuerySimple(String s) {
		if (s == null)
			return new HashMap<String, String>();
		// In map1 we use strings and ArrayLists to collect the parameter
		// values.
		HashMap<String, String> map1 = new HashMap<String, String>();
		int p = 0;
		while (p < s.length()) {
			int p0 = p;
			while (p < s.length() && s.charAt(p) != '=' && s.charAt(p) != '&')
				p++;
			String name = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '=')
				p++;
			p0 = p;
			while (p < s.length() && s.charAt(p) != '&')
				p++;
			String value = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '&')
				p++;
			String x = map1.get(name);
			if (x == null) {
				// The first value of each name is added directly as a string to
				// the map.
				map1.put(name, value);
			} else if (x instanceof String) {
				// For multiple values, we use an ArrayList.
			} else {
				//todo log not required
			}
		}
		
		return map1;
	}

	
	/**
	 *  convert map to string using a ampersand as a separator
	 * @param map
	 * @return
	 */
	public static String mapToQuery(Map<String, String> map){
		return Joiner.on("&").withKeyValueSeparator("=").join(map);
	}
	
	/**
	 * @param scheme http, https etc		(not null)
	 * @param host xx.co.kr		(not null)
	 * @param map 		(not null)
	 * @return http://xx.co.kr? + querystring
	 */
	public static String mapToQuery(String scheme, String host, Map<String, String> map){
		if(scheme == null || host == null || map == null) throw new NullPointerException();
		return scheme + "://" + host + "?" + Joiner.on("&").withKeyValueSeparator("=").join(map);
	}	
	
	
	public static void main(String[] args) {
		Map<String, String[]> map = parseQuery("aa=11&aa=2222222");
		for (Entry<String, String[]> elem : map.entrySet()) {
			if (elem.getValue() != null && elem.getKey().length() == 1) {
				System.out.println(String.format("키 : %s, 값 : %s",
						elem.getKey(), elem.getValue()));
			} else {
				for (int i = 0; i < elem.getKey().length(); i++) {
					System.out.println(String.format("키 : %s, 값 : %s",
							elem.getKey(), elem.getValue()[i]));
				}
			}
		}
		
		System.out.println("=========================");
		
		Map<String, String> mapSimple = parseQuerySimple("aa=11&aa=2222222");
		for (Entry<String, String> elem : mapSimple.entrySet()) {
			System.out.println(String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()));
		}		
	}

}
