package com.twitter.data.proto.tutorial.mapred.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.Person;
import com.twitter.elephantbird.mapred.input.DeprecatedLzoProtobufBlockInputFormat;
import com.twitter.data.proto.tutorial.mapreduce.io.ProtobufPersonWritable;
import com.twitter.elephantbird.util.TypeRef;

public class DeprecatedLzoPersonProtobufBlockInputFormat extends DeprecatedLzoProtobufBlockInputFormat<Person, ProtobufPersonWritable> {
  public DeprecatedLzoPersonProtobufBlockInputFormat() {
    setTypeRef(new TypeRef<Person>(){});
    setProtobufWritable(new ProtobufPersonWritable());
  }
}

