package com.twitter.data.proto.tutorial.mapreduce.output;

import com.twitter.data.proto.tutorial.AddressBookProtos.AddressBook;
import com.twitter.elephantbird.mapreduce.output.LzoProtobufB64LineOutputFormat;
import com.twitter.elephantbird.util.TypeRef;

public class LzoAddressBookProtobufB64LineOutputFormat extends LzoProtobufB64LineOutputFormat<AddressBook> {
  public LzoAddressBookProtobufB64LineOutputFormat() {
    super(new TypeRef<AddressBook>(){});
  }
}

