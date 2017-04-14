// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

final class MyJSONParser implements JSONParser {

  //private HashMap<String, Object> list = new HashMap<String, Object>();

  @Override
  public JSON parse(String in) throws IOException {
    if(!isValidObject(in))
      return null;

    MyJSON json = new MyJSON();

    String temp = in.trim().substring(1, in.length()-1).trim();
    if(temp.equals(""))
      return json;
    String[] elements = temp.split(",");

    if(elements.length < 1)
      return json;

    for(int i=0; i<elements.length; i++){
      String s = elements[i].trim();
      while(i < elements.length && s.contains("{") && !s.contains("}"))
        s += "," + elements[++i];

      if(!isValidPair(s))
        return null;

      int colon = s.indexOf(":");
      String key = s.substring(0, colon).trim();
      String value = s.substring(colon+1).trim();
      key = key.substring(1, key.length()-1);

      if(isValidObject(value))
        json.setObject(key, new MyJSONParser().parse(value));
      else
        json.setString(key, (value = value.substring(1, value.length()-1)));
    }

    return json;
  }

  public boolean isValidObject(String in){
    int len;
    String key, value;
    String[] elements, pair;

    in = in.trim();
    len = in.length();

    if(len < 2 || in.charAt(0) != '{' || in.charAt(len-1) != '}')
      return false;

    if(numStrings(in, "\"") / 4 > 1 && numStrings(in, ",") < 1)
      return false;

    return true;
  }

  public boolean isValidPair(String in){
    // Trim off leading/trailing whitespace
    in = in.trim();

    // Split pair into seperate key/value strings
    int colonIndex = in.indexOf(":");
    String key = in.substring(0, colonIndex).trim();
    String value = in.substring(colonIndex+1).trim();

    // If the value is a nested object, check if it's a valid object
    if(value.charAt(0) == '{' && !isValidObject(value))
      return false;

    // Else if the value is just a string, check if it's a valid string
    else if(value.charAt(0) != '{' && !isValidString(value))
      return false;

    // Pair validity depends on the key, which is always a string, so check if it's a valid key
    return isValidString(key);
  }


  public boolean isValidString(String in){
    // Trim off leading/trailing whitespace
    in = in.trim();

    int len = in.length(), numQuotes = 0;

    // If string does not contain double quote boundaries, it's invalid
    if(len < 2 || in.charAt(0) != '\"' || in.charAt(len-1) != '\"')
      return false;

    // Loop through string content and check for unsupported JSON-lite escape characters
    for(int i=1; i<len-1; i++){
      char c = in.charAt(i);

      // Record number of double quotes in string data
      if(c == '\"')
        numQuotes++;

      // If there is an unsupported escape character, return false
      if(c == '\n' || c == '\r' || c == '\f' || c == '\\' || c == '\'')
        return false;
    }

    // Since string has supported escape characters (if any), check if it has a valid amount of double quotes
    return numQuotes % 2 == 0;
  }

  public int numStrings(String in, String s){
    int len = in.length(), count = 0;
    for(int i=0; i<len; i++)
      if(in.substring(i, i+1).equals(s))
        count++;

    return count;
  }
}
