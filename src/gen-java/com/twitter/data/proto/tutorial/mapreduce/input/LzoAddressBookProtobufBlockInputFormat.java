package com.twitter.data.proto.tutorial.mapreduce.input;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapreduce.input.LzoProtobufBlockInputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoAddressBookProtobufBlockInputFormat extends LzoProtobufBlockInputFormat<AddressBook> {
  public LzoAddressBookProtobufBlockInputFormat() {
    super(new TypeRef<AddressBook>(){});
  }
}

