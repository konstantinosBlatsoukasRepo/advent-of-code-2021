package com.example.demo.days;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day14ExtendedPolymerizationTest {

  Day14ExtendedPolymerization day14 = new Day14ExtendedPolymerization();

  @Test
  public void partOneTestDay14ExtendedPolymerization_baseOnInput_shouldReturn1588L() {
    // prepare
    String input = """
 NNCB

 CH -> B
 HH -> N
 CB -> H
 NH -> C
 HB -> C
 HC -> B
 HN -> C
 NN -> C
 BH -> H
 NC -> B
 NB -> B
 BN -> B
 BB -> N
 BC -> B
 CC -> N
 CN -> C
    """;

    // act
    long result = day14.computeDiff(input, 10);

    // assert
    assertThat(result).isEqualTo(1588L);
  }

  @Test
  public void partOneDay14ExtendedPolymerization_baseOnInput_shouldReturn2712L() {
    // prepare
    String input = """
SCVHKHVSHPVCNBKBPVHV

SB -> B
HH -> P
VF -> N
BS -> S
NC -> C
BF -> H
BN -> H
SP -> H
BK -> H
FF -> N
VN -> B
FN -> C
FS -> S
PP -> F
ON -> H
FV -> F
KO -> F
PK -> H
VB -> S
HS -> B
NV -> O
PN -> S
VH -> B
OS -> P
BP -> H
OV -> B
HK -> S
NN -> K
SV -> C
PB -> F
SK -> F
FB -> S
NB -> K
HF -> P
FK -> K
KV -> P
PV -> F
BC -> S
FO -> N
HC -> F
CP -> B
KK -> F
PC -> S
HN -> O
SH -> H
CK -> P
CO -> F
HP -> K
PS -> C
KP -> F
OF -> K
KS -> F
NO -> V
CB -> K
NF -> N
SF -> F
SC -> P
FC -> V
BV -> B
SS -> O
KC -> K
FH -> C
OP -> C
CF -> K
VO -> V
VK -> H
KH -> O
NP -> V
NH -> O
NS -> V
BH -> C
CH -> S
CC -> F
CS -> P
SN -> F
BO -> S
NK -> S
OO -> P
VV -> F
FP -> V
OK -> C
SO -> H
KN -> P
HO -> O
PO -> H
VS -> N
PF -> N
CV -> F
BB -> H
VC -> H
HV -> B
CN -> S
OH -> K
KF -> K
HB -> S
OC -> H
KB -> P
OB -> C
VP -> C
PH -> K
    """;

    // act
    long result = day14.computeDiff(input, 10);

    // assert
    assertThat(result).isEqualTo(2712L);
  }

  @Test
  public void partTwoTestDay14ExtendedPolymerization_baseOnInput_shouldReturn1588L() {
    // prepare
    String input = """
 NNCB

 CH -> B
 HH -> N
 CB -> H
 NH -> C
 HB -> C
 HC -> B
 HN -> C
 NN -> C
 BH -> H
 NC -> B
 NB -> B
 BN -> B
 BB -> N
 BC -> B
 CC -> N
 CN -> C
    """;

    // act
    long result = day14.computeDiffPartTwo(input, 40);
    //    long result = day14.computeDiff(input, 40);

    // assert
    assertThat(result).isEqualTo(2188189693529L);
  }

  @Test
  public void partTwoDay14ExtendedPolymerization_baseOnInput_shouldReturn1588L() {
    // prepare
    String input = """
SCVHKHVSHPVCNBKBPVHV

SB -> B
HH -> P
VF -> N
BS -> S
NC -> C
BF -> H
BN -> H
SP -> H
BK -> H
FF -> N
VN -> B
FN -> C
FS -> S
PP -> F
ON -> H
FV -> F
KO -> F
PK -> H
VB -> S
HS -> B
NV -> O
PN -> S
VH -> B
OS -> P
BP -> H
OV -> B
HK -> S
NN -> K
SV -> C
PB -> F
SK -> F
FB -> S
NB -> K
HF -> P
FK -> K
KV -> P
PV -> F
BC -> S
FO -> N
HC -> F
CP -> B
KK -> F
PC -> S
HN -> O
SH -> H
CK -> P
CO -> F
HP -> K
PS -> C
KP -> F
OF -> K
KS -> F
NO -> V
CB -> K
NF -> N
SF -> F
SC -> P
FC -> V
BV -> B
SS -> O
KC -> K
FH -> C
OP -> C
CF -> K
VO -> V
VK -> H
KH -> O
NP -> V
NH -> O
NS -> V
BH -> C
CH -> S
CC -> F
CS -> P
SN -> F
BO -> S
NK -> S
OO -> P
VV -> F
FP -> V
OK -> C
SO -> H
KN -> P
HO -> O
PO -> H
VS -> N
PF -> N
CV -> F
BB -> H
VC -> H
HV -> B
CN -> S
OH -> K
KF -> K
HB -> S
OC -> H
KB -> P
OB -> C
VP -> C
PH -> K
    """;

    // act
    long result = day14.computeDiffPartTwo(input, 40);
    //    long result = day14.computeDiff(input, 40);

    // assert
    assertThat(result).isEqualTo(8336623059567L);
  }

}