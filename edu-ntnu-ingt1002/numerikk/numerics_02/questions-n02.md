# INGT1002 – Numerikk, øving 2



## Spørsmål 7

Et generelt tredjegradspolynom kan skrives på formen
$$
p(x)=c_0 + c_1x + c_2x^2 + c_3 x^3
$$

for koeffisienter $c_i , i = 0, 1, 2, 3$

I $x = 1$ tar dette polynomet verdien $p(1)$, slik at grafen til polynomet går gjennom punktet $(x, y) = (1, p(1))$

Verdien $p(1)$  er gitt av
$$
p(1) = c_0 + c_1 + c_2 + c_3
$$

som kan skrives som vektorproduktet

$$
p(1) = [1 1 1 1] 
\begin{bmatrix}
c_0 \\
c_1 \\
c_2 \\
c_3
\end{bmatrix} = [1 1 1 1]c
$$






Nå vil vi finne koeffisientene $c_i$ som gjør at polynomet går gjennom de fire punktene

$(−1, −2)$, $(0, 1)$, $(1, −1)$ og $(2, 4)$



Hvert punkt gir oss en ligning, og disse fire ligningene kan skrives som et lineært system $Ac = p$

for en $4 \times 4$-matrise $A$ og en vektor $p$ av polynomverdier.

Nedenfor skal du sette opp matrisen `A` og vektoren `p`, hvor vektoren skal være på formen


$$
p = 
\begin{bmatrix}
p(-1) \\
cp(0) \\
p(1) \\
p(2)
\end{bmatrix}
$$

og bruk deretter `np.linalg.solve` til å løse dette systemet og lagre svaret som variabelen `c`. 




## Spørsmål 8

For skalare ligninger $f(x) = 0$ med en skalar ukjent $x$ har vi sett at fikspunktmetoden tar utgangspunkt i å skrive om ligningen på formen $x = g(x)$.

Deretter definerer vi en fikspunkt-iterasjon gjennom $x_{n+1} = g(x_n)$.

Vi kan forsøke å bruke samme idé på det lineære systemet $Ax = b$, hvor den ukjente nå er en vektor $x$.

Dette kan vi gjøre ved å skrive $A$ som $A = D + (A - D)$, hvor $D$ er matrisen med samme diagonalelementer som $A$ og resten av elementene er null. Da får vi

$$
b = Ax = Dx + (A - D)x.
$$

Med dette uttrykket kan vi lage oss en fikspunktiterasjon

$$
D x_{n+1} = b - (A - D)x_n.
$$

Så lenge diagonalelementene i $D$ ikke er null kan vi løse dette systemet for $x_{n+1}$.

Gitt

$$
A =
\begin{bmatrix}
3 & 4 & 5 \\
1 & 6 & 2 \\
2 & 0 & 9
\end{bmatrix}, \quad b =
\begin{bmatrix}
1 \\
2 \\
3
\end{bmatrix},
$$

fullfør koden under slik at funksjonen `g(x)` tar inn en 3-vektor (et array) $x$ og returnerer løsningen av ligningen (1) for $A$ og $b$ ovenfor.

Hint: Her kan funksjonen `np.linalg.solve` være til hjelp.

Dette er såkalt Jacobi-iterasjon, en fikspunktmetode for å løse lineære systemer.

