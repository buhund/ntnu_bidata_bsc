Vektede grafer for maks flyt

|          |kilde|sluk |max flyt|
|----------|-----|-----|--------|
|flytgraf1 |0    |7    |10      |
|flytgraf2 |0    |1    |27      |
|flytgraf3 |0    |1    |?       |
|flytgraf4 |0    |7    |11      |
|flytgraf5 |0    |7    |?       |



Programmet må legge frem resultatene fra algoritmen; hver flytøkende vei, hvor mye den øker flyten, og den maksimale flyten. Det er ingen krav om tidsmålinger.
Eksempel for grafen over, hvis node 0 er kilde og node 7 er sluk:

Maksimum flyt fra 0 til 7 med Edmond-Karp
Økning : Flytøkende vei
2 0 4 3 7
1 0 4 5 6 7
1 0 1 2 3 7
1 0 1 2 3 6 7
1 0 1 4 5 6 7
1 0 1 2 3 5 6 7
1 0 1 2 4 5 6 7
2 0 1 2 3 4 5 6 7
Maksimal flyt ble 10



2 0 4 3 7
1 0 1 2 3 7
1 0 4 5 6 7
1 0 1 4* 5 6 7 ned
1 0 1 2* 3 6 7 opp
1 0 1 2 4* 5 6 7 ned
1 0 1 2 3* 5 6 7 opp
2 0 1 2 3 4 5 6 7


2 0 4 3 7
1 0 1 2 3 7
1 0 4 5 6 7
1 0 1 4 5 6 7
1 0 1 2 3 6 7
1 0 1 2 4 5 6 7
1 0 1 2 3 5 6 7
2 0 1 2 3 4 5 6 7
