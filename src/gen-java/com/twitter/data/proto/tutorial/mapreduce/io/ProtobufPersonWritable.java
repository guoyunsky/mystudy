package com.twitter.data.proto.tutorial.mapreduce.io;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapreduce.io.ProtobufWritable;
import com.twitter.elephantbird.util.TypeRef;

public class ProtobufPersonWritable extends ProtobufWritable<Person> {
  public ProtobufPersonWritable() {
    super(new TypeRef<Person>(){});
  }
  public ProtobufPersonWritable(Person m) {
    super(m, new TypeRef<Person>(){});
  }
}

