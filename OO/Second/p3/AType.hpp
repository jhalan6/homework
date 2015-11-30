#ifndef AType_hpp
#define AType_hpp
template <class T>
class AType {
    public:
        AType(){
            key=0;
        };
        AType(T t){
            key=t;
        };
        T operator+(const AType<T>& arg) const{
            //AType<T> temp(key+arg.getkey());
            return key+arg.getkey();
            //return temp;
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
