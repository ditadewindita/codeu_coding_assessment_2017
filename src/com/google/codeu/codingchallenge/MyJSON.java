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

  private HashMap<String, Object> list;

  public MyJSON(HashMap<String, Object> list){
      this.list = list;
  }

  public HashMap<String, Object> getHashMap(){
    return list;
  }

  @Override
  public JSON getObject(String name) {
    return (list.get(name) instanceof MyJSON) ? (MyJSON)list.get(name) : null;
  }

  @Override
  public JSON setObject(String name, JSON value) {
    if(list.containsKey(name) && list.get(name) instanceof MyJSON){
      list.remove(name);
      list.put(name, value);
    }

    return this;
  }

  @Override
  public String getString(String name) {
    return (list.get(name) instanceof String) ? (String)list.get(name) : null;
  }

  @Override
  public JSON setString(String name, String value) {
    if(list.containsKey(name) && list.get(name) instanceof String){
      list.remove(name);
      list.put(name, value);
    }

    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    for(String key: list.keySet())
      if(list.get(key) instanceof MyJSON)
        names.add(key);
  }

  @Override
  public void getStrings(Collection<String> names) {
    for(String key: list.keySet())
      if(list.get(key) instanceof String)
        names.add(key);
  }
}
