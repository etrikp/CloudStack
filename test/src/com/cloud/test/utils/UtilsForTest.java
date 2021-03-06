/**
 * *  Copyright (C) 2011 Citrix Systems, Inc.  All rights reserved
*
 *
 * This software is licensed under the GNU General Public License v3 or later.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.cloud.test.utils;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cloud.utils.encoding.Base64;
import com.cloud.utils.exception.CloudRuntimeException;

public class UtilsForTest {
	
	private static DocumentBuilderFactory factory = DocumentBuilderFactory
	.newInstance();
	
	public static boolean verifyTags (Map<String, String> params) {
		boolean result = true;
		for (String value : params.keySet()) {
			if (params.get(value) == null) {
				result=false;
			}
		}
		return result;
	}
	
	public static boolean verifyTagValues (Map<String, String> params, Map<String, String> pattern) {
		boolean result = true;
		
		if (pattern != null) {
			for (String value : pattern.keySet()) {
				if (!pattern.get(value).equals(params.get(value))) {
					result=false;
					System.out.println("Tag " + value + " has " + params.get(value) + " while expected value is: " + pattern.get(value));
				}
			}
		}
		return result;
	}
	
	
	public static Map<String, String> parseXML(InputStream is,
			String[] tagNames) {
		Map<String, String> returnValues = new HashMap<String, String>();
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element rootElement = doc.getDocumentElement();

			for (int i = 0; i < tagNames.length; i++) {
				NodeList targetNodes = rootElement
						.getElementsByTagName(tagNames[i]);
				if (targetNodes.getLength() <= 0) {
					System.out.println("no " + tagNames[i]
							+ " tag in the response");
					returnValues.put(tagNames[i], null);
				} else {
					returnValues.put(tagNames[i], targetNodes.item(0)
							.getTextContent());
				}
			}
		} catch (Exception ex) {
			System.out.println("error processing XML");
			ex.printStackTrace();
		}
		return returnValues;
	}
	
	
	public static ArrayList<HashMap<String, String>> parseMulXML (InputStream is, String[] tagNames){
		ArrayList<HashMap<String, String>> returnValues = new ArrayList<HashMap <String, String>>();
		
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element rootElement = doc.getDocumentElement();
			for (int i = 0; i < tagNames.length; i++) {
				NodeList targetNodes = rootElement
						.getElementsByTagName(tagNames[i]);
				if (targetNodes.getLength() <= 0) {
					System.out.println("no " + tagNames[i]
							+ " tag in XML response...returning null");
				} else {
					for (int j = 0; j < targetNodes.getLength(); j++) {
						HashMap<String, String> valueList = new HashMap<String,String> ();
						Node node = targetNodes.item(j);
						//parse child nodes
						NodeList child = node.getChildNodes();
						for (int c=0; c<node.getChildNodes().getLength(); c++){
							child.item(c).getNodeName();
							valueList.put(child.item(c).getNodeName(), child.item(c).getTextContent());
						}
						returnValues.add(valueList);
					}
					
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return returnValues;
	}
	
	
	public static String createMD5String(String password) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new CloudRuntimeException("Error", e);
		}

		md5.reset();
		BigInteger pwInt = new BigInteger(1, md5.digest(password.getBytes()));

		// make sure our MD5 hash value is 32 digits long...
		StringBuffer sb = new StringBuffer();
		String pwStr = pwInt.toString(16);
		int padding = 32 - pwStr.length();
		for (int i = 0; i < padding; i++) {
			sb.append('0');
		}
		sb.append(pwStr);
		return sb.toString();
	}
	
	
	
	
	
	
	
	public static Map<String, String> getSingleValueFromXML(InputStream is,
			String[] tagNames) {
		Map<String, String> returnValues = new HashMap<String, String>();
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element rootElement = doc.getDocumentElement();

			for (int i = 0; i < tagNames.length; i++) {
				NodeList targetNodes = rootElement
						.getElementsByTagName(tagNames[i]);
				if (targetNodes.getLength() <= 0) {
					System.out.println("no " + tagNames[i]
							+ " tag in XML response...returning null");
				} else {
					returnValues.put(tagNames[i], targetNodes.item(0)
							.getTextContent());
				}
			}
		} catch (Exception ex) {
			System.out.println("error processing XML");
			ex.printStackTrace();
		}
		return returnValues;
	}
	
	
	public static Map<String, List<String>> getMultipleValuesFromXML(
			InputStream is, String[] tagNames) {
		Map<String, List<String>> returnValues = new HashMap<String, List<String>>();
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			Element rootElement = doc.getDocumentElement();
			for (int i = 0; i < tagNames.length; i++) {
				NodeList targetNodes = rootElement
						.getElementsByTagName(tagNames[i]);
				if (targetNodes.getLength() <= 0) {
					System.out.println("no " + tagNames[i]
							+ " tag in XML response...returning null");
				} else {
					List<String> valueList = new ArrayList<String>();
					for (int j = 0; j < targetNodes.getLength(); j++) {
						Node node = targetNodes.item(j);
						valueList.add(node.getTextContent());
					}
					returnValues.put(tagNames[i], valueList);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return returnValues;
	}
	
	
	
	public static String signRequest(String request, String key) {
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),
					"HmacSHA1");
			mac.init(keySpec);
			mac.update(request.getBytes());
			byte[] encryptedBytes = mac.doFinal();
			//System.out.println("HmacSHA1 hash: " + encryptedBytes);
			return Base64.encodeBytes(encryptedBytes);
		} catch (Exception ex) {
			System.out.println("unable to sign request");
			ex.printStackTrace();
		}
		return null;
	}
	
}
