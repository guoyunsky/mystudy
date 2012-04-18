package com.twitter.data.proto.tutorial.mapreduce.output;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapreduce.output.LzoProtobufBlockOutputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoAddressBookProtobufBlockOutputFormat extends LzoProtobufBlockOutputFormat<AddressBook> {
  public LzoAddressBookProtobufBlockOutputFormat() {
    super(new TypeRef<AddressBook>(){});
  }
}

