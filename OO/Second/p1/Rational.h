#ifndef Rational_hpp
#define Rational_hpp

class Rational{
    public:
        Rational(int left=0,int right=0);
        Rational operator+(const Rational&) const;
        Rational operator*(const Rational&) const;
        Rational &operator=(const Rational&);
        bool operator==(const Rational&) const;
        char* value();
    private:
        char val[64];
        int l;
        int r;
};

#endif
