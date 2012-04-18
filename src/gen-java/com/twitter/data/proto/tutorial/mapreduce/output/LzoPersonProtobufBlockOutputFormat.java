package com.twitter.data.proto.tutorial.mapreduce.output;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapreduce.output.LzoProtobufBlockOutputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoPersonProtobufBlockOutputFormat extends LzoProtobufBlockOutputFormat<Person> {
  public LzoPersonProtobufBlockOutputFormat() {
    super(new TypeRef<Person>(){});
  }
}

