package com.twitter.data.proto.tutorial.mapreduce.output;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapreduce.output.LzoProtobufB64LineOutputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoPersonProtobufB64LineOutputFormat extends LzoProtobufB64LineOutputFormat<Person> {
  public LzoPersonProtobufB64LineOutputFormat() {
    super(new TypeRef<Person>(){});
  }
}

