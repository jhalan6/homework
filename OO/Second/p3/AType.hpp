#ifndef AType_hpp
#define AType_hpp
template <class T>
class AType {
    public:
        AType(T t=0):key(t){ };
        T operator+(const AType<T>& arg) const{
            return key+arg.getkey();
        };
        T operator-(const AType<T>& arg) const{
            return key-arg.getkey();
        };
        int operator>(const AType<T>& arg) const{
            return check(key-arg.getkey());
        };
        int operator<(const AType<T>& arg) const{
            return check(-key+arg.getkey());
        };
        T getkey()const{
            return key;
        };
    private:
        T key;
        int check(T t)const{
            if(t>0){
                return 1;
            }else if(t==0){
                return 0;
            }else{
                return -1;
            }
        }
};

#endif
