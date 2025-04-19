public class Fraction {
    // Klassens datamedlemmer
    private int _numerator, _denominator;

    // Fractions for use in operations.
    public static void main(String[] args) {
        Fraction frac1 = new Fraction(3,2);
        Fraction frac2 = new Fraction(6,5);
        //Fraction frac3 = new Fraction(2,5);
        //Fraction frac4 = new Fraction(5,6);
        //Fraction frac5 = new Fraction(6,3);


        frac1.add(frac2);
        //frac3.add(frac4);
        //frac5.add(frac2);

        System.out.println("f1+f2: Should be 27/10, actual: " + frac1.getNumerator() + "/" + frac1.getDenominator());
        //System.out.println("f3+f4: Should be 37/30, actual: " + frac3.getNumerator() + "/" + frac3.getDenominator());
        //System.out.println("f5+f2: Should be 30/6 (or 5), actual: " + frac5.getNumerator() + "/" + frac5.getDenominator());
        System.out.println();

        frac1.subtract(frac2);
        System.out.println("f1-f2: Should be 3/2, actual: " + frac1.getNumerator() + "/" + frac1.getDenominator());
        System.out.println();

        frac1.multiply(frac2);
        System.out.println("f1*f2: Should be 9/5, actual: " + frac1.getNumerator() + "/" + frac1.getDenominator());
        System.out.println();

        frac1.divide(frac2);
        System.out.println("f1/f2: Should be 3/2, actual: " + frac1.getNumerator() + "/" + frac1.getDenominator());
        System.out.println();


    }

    // Én konstruktør som tar inn teller og nevner
    public Fraction(int _numerator, int _denominator) {
        // Om nevner er 0, kast en exception
        if(_denominator==0)
            throw new IllegalArgumentException("Denominator cannot be zero");
        this._numerator = _numerator;
        this._denominator = _denominator;
    }

    // Én konstruktør som tar kun teller. Nevner settes til 1
    public Fraction(int _numerator) {
        this._numerator = _numerator;
        this._denominator = 1;
    }

    //Get-metoder
    public int getNumerator() {
        return _numerator;
    }
    public int getDenominator() {
        return _denominator;
    }

    // Legg en brøk til en annen brøk
    public void add(Fraction fraction){
        this._numerator = this._numerator * fraction.getDenominator();
        int tempDenominator = this._denominator;
        this._denominator = this._denominator * fraction.getDenominator();

        this._numerator = this._numerator + (tempDenominator * fraction.getNumerator());
        normalize();
    }
    
    // Trekke fra en annen brøk
    public void subtract(Fraction fraction){
        this._numerator = this._numerator * fraction.getDenominator();
        int tempDenominator = this._denominator;
        this._denominator = this._denominator * fraction.getDenominator();

        this._numerator = this._numerator - (tempDenominator * fraction.getNumerator());
        normalize();
    }
    public void multiply(Fraction fraction){
        this._numerator = this._numerator * fraction.getNumerator();
        this._denominator = this._denominator * fraction.getDenominator();

        normalize();
    }
    public void divide(Fraction fraction){
        this._numerator = this._numerator * fraction.getDenominator();
        this._denominator = this._denominator * fraction.getNumerator();
        normalize();
    }

    private void normalize(){
        // Implementer
    }
}