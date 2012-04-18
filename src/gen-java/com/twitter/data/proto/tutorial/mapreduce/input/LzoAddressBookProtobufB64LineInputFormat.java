package com.twitter.data.proto.tutorial.mapreduce.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapreduce.input.LzoProtobufB64LineInputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoAddressBookProtobufB64LineInputFormat extends LzoProtobufB64LineInputFormat<AddressBook> {
  public LzoAddressBookProtobufB64LineInputFormat() {
    super(new TypeRef<AddressBook>(){});
  }
}

