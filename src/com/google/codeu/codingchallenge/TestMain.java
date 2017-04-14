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
import java.util.HashSet;

final class TestMain {

  public static void main(String[] args) throws Exception{

    final Tester tests = new Tester();

    tests.add("Empty Object", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ }");

        final Collection<String> strings = new HashSet<>();
        obj.getStrings(strings);

        Asserts.isEqual(strings.size(), 0);

        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });

    tests.add("String Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":\"sam doe\" }");

        Asserts.isEqual("sam doe", obj.getString("name"));
     }
    });

    tests.add("Object Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });

    // Extra unit test
    tests.add("Object Value 2", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{\"name\" : \"dita\", \"age\" : \"19\", \"favorites\" : {\"chicken\" : \"nuggets\", \"cheese\" : \"bread\", \"drinks\" : {\"iced\" : \"tea\"}}, \"height\" : \"5 foot 5 in\"}");

        final JSON favObj = obj.getObject("favorites");

        Asserts.isNotNull(favObj);
        Asserts.isEqual("nuggets", favObj.getString("chicken"));
        Asserts.isEqual("bread", favObj.getString("cheese"));

        final JSON drinksObj = favObj.getObject("drinks");
        Asserts.isNotNull(drinksObj);
        Asserts.isEqual("tea", drinksObj.getString("iced"));
      }
    });

    tests.run(new JSONFactory(){
      @Override
      public JSONParser parser() {
        return new MyJSONParser();
      }

      @Override
      public JSON object() {
        return new MyJSON();
      }
    });

    // final JSONParser parser = new MyJSONParser();
    // final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");
    //
    // final JSON nameObj = obj.getObject("name");
    //
    // System.out.println(nameObj.getObject("name"));

    // JSONParser parser = new MyJSONParser();
    // JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");
    // JSON nameObj = obj.getObject("name");
    // System.out.println(nameObj.getString("first"));

    // JSON json = parser.parse("{\"name\" : \"dita\", \"age\" : \"19\", \"favorites\" : {\"chicken\" : \"nuggets\", \"cheese\" : \"bread\"}, \"height\" : \"5 foot 5 in\"}");
    // System.out.println(json.getObject("\"favorites\"").getString("\"cheese\""));

    //System.out.println(parser.parse("{\"name\" : \"dita\", \"age\" : \"19\", \"favorites\" : {\"chicken\" : \"nuggets\", \"cheese\" : \"bread\"}, \"height\" : \"5 foot 5 in\"}"));
    //System.out.println(parser.isValidPair("\"name\" : {\"name\" : \"uyo\"}"));
    //System.out.println(parser.parse("{\"pseudo\" : \"dita\"}"));
  }
}
