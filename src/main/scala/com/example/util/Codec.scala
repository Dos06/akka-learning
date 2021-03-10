package com.example.util

import com.example.models.Item.Item
import com.example.models.Summary

trait Codec {
  implicit val itemEncodeDecode: EncoderDecoder[Item] = DerivedEncoderDecoder[Item]
  implicit val summaryEncodeDecode: EncoderDecoder[Summary] = DerivedEncoderDecoder[Summary]
}
