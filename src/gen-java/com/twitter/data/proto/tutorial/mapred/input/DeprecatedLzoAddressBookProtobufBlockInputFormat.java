package com.twitter.data.proto.tutorial.mapred.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapred.input.DeprecatedLzoProtobufBlockInputFormat;
import com.twitter.data.proto.tutorial.mapreduce.io.ProtobufAddressBookWritable;
import com.twitter.elephantbird.util.TypeRef;

public class DeprecatedLzoAddressBookProtobufBlockInputFormat extends DeprecatedLzoProtobufBlockInputFormat<AddressBook, ProtobufAddressBookWritable> {
  public DeprecatedLzoAddressBookProtobufBlockInputFormat() {
    setTypeRef(new TypeRef<AddressBook>(){});
    setProtobufWritable(new ProtobufAddressBookWritable());
  }
}

