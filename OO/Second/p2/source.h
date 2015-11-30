#ifndef source_hpp
#define source_hpp

class Base {
    protected:
        int iBody;
    public:
        virtual void printOn() = 0;
        Base(int i = 0) : iBody(i) {}
};
class Sub1 : public Base {
    // ...
    public:
        void printOn();
        Sub1(int i,char* s);
    private:
        char* s;
};
class Sub2 : public Base { // ...
    public:
        void printOn();
        Sub2(int i,short s);
    private:
        short s;
};


#endif 
