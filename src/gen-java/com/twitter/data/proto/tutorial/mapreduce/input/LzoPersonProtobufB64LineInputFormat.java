package com.twitter.data.proto.tutorial.mapreduce.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapreduce.input.LzoProtobufB64LineInputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoPersonProtobufB64LineInputFormat extends LzoProtobufB64LineInputFormat<Person> {
  public LzoPersonProtobufB64LineInputFormat() {
    super(new TypeRef<Person>(){});
  }
}

