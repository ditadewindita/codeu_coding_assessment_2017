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

import java.util.Collection;
import java.util.HashMap;

final class MyJSON implements JSON {

  // Instance variables
  private HashMap<String, Object> list;

  public MyJSON(){
      list = new HashMap<String, Object>();
  }

  // Getters
  public HashMap<String, Object> getHashMap(){
    return list;
  }

  // Setters
  public void setHashMap(HashMap<String, Object> h){
    list = h;
  }

  @Override
  public JSON getObject(String name) {
    // If the pairing value of 'name' is an object, return it, otherwise return null
    return (list.get(name) instanceof JSON) ? (JSON)list.get(name) : null;
  }

  @Override
  public JSON setObject(String name, JSON value) {
    // If the pair exist already, remove it
    if(list.containsKey(name) && list.get(name) instanceof JSON)
      list.remove(name);

    // Put the pair in the list
    list.put(name, value);

    return this;
  }

  @Override
  public String getString(String name) {
    // If the pairing value of 'name' is a string, return it, otherwise return null
    return (list.get(name) instanceof String) ? (String)list.get(name) : null;

  }

  @Override
  public JSON setString(String name, String value) {
    // If the pair exists already, remove it
    if(list.containsKey(name) && list.get(name) instanceof String)
      list.remove(name);

    // Put the pair in the list
    list.put(name, value);

    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    // Iterate through the keys of the hash map, and if the key is an object, add to the collection
    for(String key: list.keySet())
      if(list.get(key) instanceof JSON)
        names.add(key);
  }

  @Override
  public void getStrings(Collection<String> names) {
    // Iterate through the keys of the hash map, and if the key is an string, add to the collection
    for(String key: list.keySet())
      if(list.get(key) instanceof String)
        names.add(key);
  }
}
