public class HelloWorld {
    public static class SubClass1 {
        public int i;
        public void publicMethod() {
            System.out.println( "SubClass1::publicMethod()" );
        }

        private void privateMethod() {
            System.out.println( "SubClass1::privateMethod()" );
        }

        public static void staticMethod() {
            System.out.println( "SubClass1::staticMethod()" );
        }

        private void nonstaticMethod() {
            i = i + 1;
            int j = 0;
            j = j + 1;
            System.out.println( "SubClass1::nonstaticMethod(); i = " + i + " J = " + j );
        }
    };

    public static void main(String[] args) {
        String output = "Hello, World!!! patched from here";
        output = output + "!!!";
        print( output );

        int x1 = 87;
        int y1 = 67;
        int number = calculateValue(x1, y1);
        System.out.println(number);

        System.out.println("calling object methods:");
        SubClass1 subclass = new SubClass1();
        subclass.privateMethod();
        subclass.publicMethod();
        subclass.staticMethod();
        subclass.nonstaticMethod();

        System.out.println("calling class methods:" );
        //SubClass1.privateMethod();
        //SubClass1.publicMethod();
        SubClass1.staticMethod();
        //SubClass1.nonstaticMethod();
        // x++  == x = x + 1
        int x = 0;
        for( ; x<10; x++) {

            for ( int y = 2; y<x; y++ ) {
                int remainder = x%y;

                System.out.println("x=" +x + " y=" +y +" %=" + remainder );
            }

            System.out.println("--");
        }


    }

    public static void print( String value ) {
        System.out.println( value );
    }


    public static int calculateValue(int a, int b) {
        return a+b;
    }
}

