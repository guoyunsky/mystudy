package com.twitter.data.proto.tutorial.mapreduce.io;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapreduce.io.ProtobufWritable;
import com.twitter.elephantbird.util.TypeRef;

public class ProtobufAddressBookWritable extends ProtobufWritable<AddressBook> {
  public ProtobufAddressBookWritable() {
    super(new TypeRef<AddressBook>(){});
  }
  public ProtobufAddressBookWritable(AddressBook m) {
    super(m, new TypeRef<AddressBook>(){});
  }
}

