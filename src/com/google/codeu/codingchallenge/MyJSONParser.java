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

final class MyJSONParser implements JSONParser {

  private HashMap<String, Object> list;

  @Override
  public JSON parse(String in) throws IOException {
    list = new HashMap<String, Object>();

    boolean valid = isValidObject(in);
    if(!valid)
      return null;

    return new MyJSON(list);
  }

  public boolean isValidObject(String in){
    int len = in.length();
    String[] elements;

    // Trim off leading/trailing whitespace
    in = in.trim();

    // If there is no room for bracket pair, or if brackets aren't in the appropriate spot, return false
    if(len < 2 || in.charAt(0) != '{' || in.charAt(len-1) != '}')
      return false;

    // Split object's elements into string array
    in = in.substring(1, len-1);
    elements = in.split(",");

    System.out.println(in);
    // Since elements string array's split is dependent on a comma, exlude the array's elements which include the object's brackets
    for(int i=0; i<elements.length; i++){
      String s = elements[i].trim();

      if(s.charAt(0) == '{' && !isValidObject(s))
        return false;

      else if(!isValidPair(s))
        return false;

      // list.put(s.substring(0, s.indexOf(":")).trim(), s.substring(s.indexOf(":")).trim()));
    }

    return true;
  }

  public boolean isValidPair(String in){
    int j, len = in.length(), midStart = -1, midEnd = -1, spaceBtwn = 0, numColon = 0;

    // Trim off leading/trailing whitespace
    in = in.trim();

    // If string doesn't have room for two sets of double quotes and colon or it doesn't contain a colon, it's an invalid pair
    if(len < 5 || !in.contains(":"))
      return false;

    for(int i=0; i<len; i++){
      char c = in.charAt(i);
      if(c == '\"'){
        // Get index of end double quote of current key/value
        for(j=i+1; in.charAt(j) != '\"'; j++);

        // If key/value isn't a valid string, return false
        if(!isValidString(in.substring(i, j+1)))
          return false;

        // Record index after key is visited to check for colon
        if(i == 0 && midStart == -1)
          midStart = j+1;

        // Record index before value is visited to check for colon
        if(i > 0 && midEnd == -1)
          midEnd = i-1;

        // Jump to index j (end of key/value string) since already checked if string is valid
        i = j;
      }
    }

    // Since key/value are valid strings, return if there only exists a colon between
    return in.substring(midStart, midEnd+1).trim().equals(":");
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

}
