package com.twitter.data.proto.tutorial.mapreduce.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapreduce.input.LzoProtobufBlockInputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoPersonProtobufBlockInputFormat extends LzoProtobufBlockInputFormat<Person> {
  public LzoPersonProtobufBlockInputFormat() {
    super(new TypeRef<Person>(){});
  }
}

