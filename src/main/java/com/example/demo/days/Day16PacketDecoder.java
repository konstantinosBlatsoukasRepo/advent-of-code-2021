package com.example.demo.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day16PacketDecoder {

  private enum TypeId {
    LITERAL,
    OPERATION
  }

  private enum LengthTypeId {
    ZERO,
    ONE
  }

  private record SubPacketsWithEndIndex(int totalSubPackets, int endPacketIndex) {}

  private record LiteralBinaryWithEndIndex(String literalBinary, int endIndex) {}

  private static class Packet {
    private int version;

    private TypeId typeId;

    private LengthTypeId lengthTypeId;

    private String literalBinary;

    private int packetStartIndex;

    private int packetEndIndex;

    public List<Packet> getSubPackets() {
      return subPackets;
    }

    public int getPacketEndIndex() {
      return packetEndIndex;
    }

    public void setPacketEndIndex(int packetEndIndex) {
      this.packetEndIndex = packetEndIndex;
    }

    public int getPacketStartIndex() {
      return packetStartIndex;
    }

    public void setPacketStartIndex(int packetStartIndex) {
      this.packetStartIndex = packetStartIndex;
    }

    public String getLiteralBinary() {
      return literalBinary;
    }

    public void setLiteralBinary(String literalBinary) {
      this.literalBinary = literalBinary;
    }

    private final List<Packet> subPackets = new ArrayList<>();

    public int getVersion() {
      return version;
    }

    public void setVersion(int version) {
      this.version = version;
    }

    public TypeId getTypeId() {
      return typeId;
    }

    public void setTypeId(TypeId typeId) {
      this.typeId = typeId;
    }

    public LengthTypeId getLengthTypeId() {
      return lengthTypeId;
    }

    public void setLengthTypeId(LengthTypeId lengthTypeId) {
      this.lengthTypeId = lengthTypeId;
    }
  }

  // part one
  public int partOne(String input) {
    String padLeftZeros = "";
    if (input.charAt(0) == '1') {
      padLeftZeros = "000";
    }

    if (input.charAt(0) >= '2' && input.charAt(0) <= '3') {
      padLeftZeros = "00";
    }

    if (input.charAt(0) >= '4' && input.charAt(0) <= '7') {
      padLeftZeros = "0";
    }

    String binaryFormat = padLeftZeros + new BigInteger(input, 16).toString(2);

    int packetStartIndex = 0;
    Packet rootPacket = parsePacket(binaryFormat, packetStartIndex);

    return calculateVersionsSum(rootPacket);
  }

  private int calculateVersionsSum(Packet rootPacket) {
    Queue<Packet> queue = new LinkedList<>();
    queue.add(rootPacket);
    int versionsSum = 0;
    while (!queue.isEmpty()) {
      Packet packet = queue.remove();
      versionsSum += packet.getVersion();
      queue.addAll(packet.getSubPackets());
    }
    return versionsSum;
  }

  private Packet parsePacket(String binaryFormat, int packetStartIndex) {
    Packet packet = new Packet();

    packet.setVersion(parseVersion(packetStartIndex, binaryFormat));
    packet.setTypeId(parseTypeId(packetStartIndex, binaryFormat));

    TypeId typeId = packet.getTypeId();
    switch (typeId) {
      case LITERAL -> {
        LiteralBinaryWithEndIndex literalBinaryWithEndIndex = parseLiteralBinary(typeId, packetStartIndex, binaryFormat);
        packet.setLiteralBinary(literalBinaryWithEndIndex.literalBinary());
        packet.setPacketEndIndex(literalBinaryWithEndIndex.endIndex());
      }
      case OPERATION  -> {
        LengthTypeId lengthTypeId = parseLengthTypeId(typeId, packetStartIndex, binaryFormat);
        packet.setLengthTypeId(lengthTypeId);
        SubPacketsWithEndIndex subPacketsWithEndIndex;
        if (LengthTypeId.ONE == lengthTypeId) {
          subPacketsWithEndIndex = parseTotalSubPackets(packetStartIndex, binaryFormat);

          packet.setPacketEndIndex(subPacketsWithEndIndex.endPacketIndex());

          int packetEndIndex = packet.getPacketEndIndex();
          for (int currentSubPacket = 0; currentSubPacket < subPacketsWithEndIndex.totalSubPackets(); currentSubPacket++) {
            Packet newPacket = parsePacket(binaryFormat, packetEndIndex);
            packetEndIndex = newPacket.getPacketEndIndex();
            packet.getSubPackets().add(newPacket);
          }

          if (!packet.getSubPackets().isEmpty()) {
            int newPacketEndIndex = packet.getSubPackets().get(packet.getSubPackets().size() - 1).getPacketEndIndex();
            packet.setPacketEndIndex(newPacketEndIndex);
          }

        } else {
          subPacketsWithEndIndex = parseTotalLengthOfSubPackets(packetStartIndex, binaryFormat);

          int totalLengthSubPackets = subPacketsWithEndIndex.totalSubPackets();
          int packetEndIndex = subPacketsWithEndIndex.endPacketIndex();

          int goal = packetEndIndex + totalLengthSubPackets;
          while (packetEndIndex != goal) {
            Packet newPacket = parsePacket(binaryFormat, packetEndIndex);
            packetEndIndex = newPacket.getPacketEndIndex();
            packet.getSubPackets().add(newPacket);
            packet.setPacketEndIndex(packetEndIndex);
          }

        }
      }
    }
    return packet;
  }

  private int parseVersion(int startIndex, String binaryFormat) {
    return  Integer.parseInt(binaryFormat.substring(startIndex, startIndex + 3), 2);
  }

  private LengthTypeId parseLengthTypeId(TypeId typeId, int startIndex, String binaryFormat) {
    if (typeId != TypeId.OPERATION) {
      throw new IllegalCallerException("the type id must be OPERATION");
    }

    if (Integer.parseInt(binaryFormat.substring(startIndex + 6,  startIndex + 7), 2) == 0) {
      return LengthTypeId.ZERO;
    }
    return LengthTypeId.ONE;
  }

  private SubPacketsWithEndIndex parseTotalSubPackets(int startIndex, String binaryFormat) {
    int totalSubPackets = Integer.parseInt(binaryFormat.substring(startIndex + 7,  startIndex + 7 + 11), 2);
    return new SubPacketsWithEndIndex(totalSubPackets, startIndex + 7 + 11);
  }

  private SubPacketsWithEndIndex parseTotalLengthOfSubPackets(int startIndex, String binaryFormat) {
    int totalLengthSubPackets = Integer.parseInt(binaryFormat.substring(startIndex + 7,  startIndex + 7 + 15), 2);
    return new SubPacketsWithEndIndex(totalLengthSubPackets, startIndex + 7 + 15);
  }

  private TypeId parseTypeId(int startIndex, String binaryFormat) {
    if (Integer.parseInt(binaryFormat.substring(startIndex + 3, startIndex + 6), 2) == 4) {
      return TypeId.LITERAL;
    }
    return TypeId.OPERATION;
  }

  private LiteralBinaryWithEndIndex parseLiteralBinary(TypeId typeId, int startIndex, String binaryFormat) {
    if (typeId != TypeId.LITERAL) {
      throw new IllegalCallerException("the type id must be LITERAL");
    }

    int startCharPointer = startIndex + 6;
    int endCharPointer = startIndex + 11;
    StringBuilder binVal = new StringBuilder();
    String currentVal;
    do {
      currentVal = binaryFormat.substring(startCharPointer, endCharPointer);
      binVal.append(binaryFormat.substring(startCharPointer + 1, endCharPointer));

      startCharPointer += 5;
      endCharPointer += 5;

    } while (currentVal.charAt(0) != '0');

    return new LiteralBinaryWithEndIndex(binVal.toString(), endCharPointer - 5);
  }

}
