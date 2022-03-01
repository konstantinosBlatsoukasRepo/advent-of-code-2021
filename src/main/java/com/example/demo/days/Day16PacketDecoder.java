package com.example.demo.days;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day16PacketDecoder {

  private enum TypeOperationOrLiteral {
    SUM,
    PRODUCT,
    MINIMUM,
    MAXIMUM,
    LITERAL,
    GRATER_THAN,
    LESS_THAN,
    EQUAL}

  private record TypeId(int typeId) {

    public TypeOperationOrLiteral getType() {
      switch (this.typeId()) {
        case 0 -> { return TypeOperationOrLiteral.SUM; }
        case 1 -> { return TypeOperationOrLiteral.PRODUCT; }
        case 2 -> { return TypeOperationOrLiteral.MINIMUM; }
        case 3 -> { return TypeOperationOrLiteral.MAXIMUM; }
        case 4 -> { return TypeOperationOrLiteral.LITERAL; }
        case 5 -> { return TypeOperationOrLiteral.GRATER_THAN; }
        case 6 -> { return TypeOperationOrLiteral.LESS_THAN; }
        case 7 -> { return TypeOperationOrLiteral.EQUAL; }
        default -> throw new IllegalStateException();
      }
    }
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
    String binaryFormat = getBinaryFormat(input);

    int packetStartIndex = 0;
    Packet rootPacket = parsePacket(binaryFormat, packetStartIndex);

    return calculateVersionsSum(rootPacket);
  }

  // part two
  public BigDecimal partTwo(String input) {
    String binaryFormat = getBinaryFormat(input);
    int packetStartIndex = 0;
    Packet rootPacket = parsePacket(binaryFormat, packetStartIndex);
    return evaluateTransmissionPacket(rootPacket);
  }

  private String getBinaryFormat(String input) {
    String padLeftZeros = "";

    if (input.charAt(0) == '0' && input.charAt(1) == '4') {
      padLeftZeros = "00000";
    }
    if (input.charAt(0) == '1') {
      padLeftZeros = "000";
    }

    if (input.charAt(0) >= '2' && input.charAt(0) <= '3') {
      padLeftZeros = "00";
    }

    if (input.charAt(0) >= '4' && input.charAt(0) <= '7') {
      padLeftZeros = "0";
    }

    return padLeftZeros + new BigInteger(input, 16).toString(2);
  }

  private BigDecimal evaluateTransmissionPacket(Packet packet) {
    switch (packet.getTypeId().getType()) {
      case SUM -> {
        BigDecimal sum = BigDecimal.valueOf(0L);
        for (Packet subPacket : packet.getSubPackets()) {
          sum = sum.add(evaluateTransmissionPacket(subPacket));
        }
        return sum;
      }
      case PRODUCT -> {
        BigDecimal product = BigDecimal.valueOf(1L);
        for (Packet subPacket : packet.getSubPackets()) {
          product = product.multiply(evaluateTransmissionPacket(subPacket));
        }
        return product;
      }
      case MINIMUM -> {
        BigDecimal minimum = BigDecimal.valueOf(Long.MAX_VALUE);
        for (Packet subPacket : packet.getSubPackets()) {
          BigDecimal newMinVal = evaluateTransmissionPacket(subPacket);
          if (minimum.compareTo(newMinVal) > 0) {
            minimum = newMinVal;
          }
        }
        return minimum;
      }
      case MAXIMUM -> {
        BigDecimal maximum = BigDecimal.valueOf(Long.MIN_VALUE);
        for (Packet subPacket : packet.getSubPackets()) {
          BigDecimal newMaxVal = evaluateTransmissionPacket(subPacket);
          if (maximum.compareTo(newMaxVal) < 0) {
            maximum = newMaxVal;
          }
        }
        return maximum;
      }
      case LITERAL -> { return new BigDecimal(new BigInteger(packet.getLiteralBinary(), 2)); }
      case GRATER_THAN -> {
        List<Packet> subPackets = packet.getSubPackets();
        BigDecimal firstSubPacketValue = evaluateTransmissionPacket(subPackets.get(0));
        BigDecimal secondSubPacketValue = evaluateTransmissionPacket(subPackets.get(1));
        if (firstSubPacketValue.compareTo(secondSubPacketValue) > 0) {
          return BigDecimal.valueOf(1L);
        }
        return BigDecimal.valueOf(0L);
      }
      case LESS_THAN -> {
        List<Packet> subPackets = packet.getSubPackets();
        BigDecimal firstSubPacketValue = evaluateTransmissionPacket(subPackets.get(0));
        BigDecimal secondSubPacketValue = evaluateTransmissionPacket(subPackets.get(1));
        if (firstSubPacketValue.compareTo(secondSubPacketValue) < 0) {
          return BigDecimal.valueOf(1L);
        }
        return BigDecimal.valueOf(0L);
      }
      case EQUAL -> {
        List<Packet> subPackets = packet.getSubPackets();
        BigDecimal firstSubPacketValue = evaluateTransmissionPacket(subPackets.get(0));
        BigDecimal secondSubPacketValue = evaluateTransmissionPacket(subPackets.get(1));
        if (firstSubPacketValue.compareTo(secondSubPacketValue) == 0) {
          return BigDecimal.valueOf(1L);
        }
        return BigDecimal.valueOf(0L);
      }
      default -> throw new IllegalStateException();
    }
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

    if (typeId.getType() == TypeOperationOrLiteral.LITERAL) {
      LiteralBinaryWithEndIndex literalBinaryWithEndIndex = parseLiteralBinary(typeId, packetStartIndex, binaryFormat);
      packet.setLiteralBinary(literalBinaryWithEndIndex.literalBinary());
      packet.setPacketEndIndex(literalBinaryWithEndIndex.endIndex());
    } else {
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
    return packet;
  }

  private int parseVersion(int startIndex, String binaryFormat) {
    return  Integer.parseInt(binaryFormat.substring(startIndex, startIndex + 3), 2);
  }

  private LengthTypeId parseLengthTypeId(TypeId typeId, int startIndex, String binaryFormat) {
    if (typeId.getType() == TypeOperationOrLiteral.LITERAL) {
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
    int typeCodeIdRaw = Integer.parseInt(binaryFormat.substring(startIndex + 3, startIndex + 6), 2);
    return new TypeId(typeCodeIdRaw);
  }

  private LiteralBinaryWithEndIndex parseLiteralBinary(TypeId typeId, int startIndex, String binaryFormat) {
    if (typeId.getType() != TypeOperationOrLiteral.LITERAL) {
      throw new IllegalCallerException("the type id must be LITERAL");
    }

    int startCharPointer = startIndex + 6;
    int endCharPointer = startIndex + 11;
    StringBuilder binVal = new StringBuilder();
    String currentVal;
    do {
      currentVal = binaryFormat.substring(startCharPointer, endCharPointer);
      binVal.append(binaryFormat, startCharPointer + 1, endCharPointer);

      startCharPointer += 5;
      endCharPointer += 5;

    } while (currentVal.charAt(0) != '0');

    return new LiteralBinaryWithEndIndex(binVal.toString(), endCharPointer - 5);
  }

}
