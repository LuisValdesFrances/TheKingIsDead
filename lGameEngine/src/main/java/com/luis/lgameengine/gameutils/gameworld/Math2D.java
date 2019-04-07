package com.luis.lgameengine.gameutils.gameworld;


public class Math2D {
	
	
//	public static int convertAngleToByte(int _angle) {
//        int correctedAngle = _angle % 360;
//        if (correctedAngle < 0) {
//            correctedAngle += 360;
//        }
//        return ((correctedAngle) * SINCOS.length) / 360;
//    }
//	
//	//public static final int MAX_VALUE_TABLE = 64571;
//	public static final int SINCOS_PRECISIONBITS_DEP = 16;
//	public static int[] SINCOS  = {
//        // 0
//            0,    804,   1608,   2412,   3215,   4018,   4821,   5622,   6423,   7223,   8022,   8819,   9616,  10410,  11204,  11995,  12785,  13573,  14359,  15142,  15923,  16702,  17479,  18253,  19024,  19792,  20557,  21319,  22078,  22833,  23586,  24334,
//        25079,  25820,  26557,  27291,  28020,  28745,  29465,  30181,  30893,  31600,  32302,  32999,  33692,  34379,  35061,  35738,  36409,  37075,  37736,  38390,  39039,  39682,  40319,  40950,  41575,  42194,  42806,  43412,  44011,  44603,  45189,  45768,
//        46340,  46906,  47464,  48015,  48558,  49095,  49624,  50146,  50660,  51166,  51665,  52155,  52639,  53114,  53581,  54040,  54491,  54933,  55368,  55794,  56212,  56621,  57022,  57414,  57797,  58172,  58538,  58895,  59243,  59583,  59913,  60235,
//        60547,  60850,  61144,  61429,  61705,  61971,  62228,  62475,  62714,  62942,  63162,  63371,  63571,  63762,  63943,  64115,  64276,  64428,  64571,  64703,  64826,  64939,  65043,  65136,  65220,  65294,  65358,  65412,  65457,  65491,  65516,  65531,
//        65536,  65531,  65516,  65491,  65457,  65412,  65358,  65294,  65220,  65136,  65043,  64939,  64826,  64703,  64571,  64428,  64276,  64115,  63943,  63762,  63571,  63371,  63162,  62942,  62714,  62475,  62228,  61971,  61705,  61429,  61144,  60850,
//        60547,  60235,  59913,  59583,  59243,  58895,  58538,  58172,  57797,  57414,  57022,  56621,  56212,  55794,  55368,  54933,  54491,  54040,  53581,  53114,  52639,  52155,  51665,  51166,  50660,  50146,  49624,  49095,  48558,  48015,  47464,  46906,
//        46340,  45768,  45189,  44603,  44011,  43412,  42806,  42194,  41575,  40950,  40319,  39682,  39039,  38390,  37736,  37075,  36409,  35738,  35061,  34379,  33692,  32999,  32302,  31600,  30893,  30181,  29465,  28745,  28020,  27291,  26557,  25820,
//        25079,  24334,  23586,  22833,  22078,  21319,  20557,  19792,  19024,  18253,  17479,  16702,  15923,  15142,  14359,  13573,  12785,  11995,  11204,  10410,   9616,   8819,   8022,   7223,   6423,   5622,   4821,   4018,   3215,   2412,   1608,    804,
//
//        // 256
//             0,   -804,  -1608,  -2412,  -3215,  -4018,  -4821,  -5622,  -6423,  -7223,  -8022,  -8819,  -9616, -10410, -11204, -11995, -12785, -13573, -14359, -15142, -15923, -16702, -17479, -18253, -19024, -19792, -20557, -21319, -22078, -22833, -23586, -24334,
//        -25079, -25820, -26557, -27291, -28020, -28745, -29465, -30181, -30893, -31600, -32302, -32999, -33692, -34379, -35061, -35738, -36409, -37075, -37736, -38390, -39039, -39682, -40319, -40950, -41575, -42194, -42806, -43412, -44011, -44603, -45189, -45768,
//        -46340, -46906, -47464, -48015, -48558, -49095, -49624, -50146, -50660, -51166, -51665, -52155, -52639, -53114, -53581, -54040, -54491, -54933, -55368, -55794, -56212, -56621, -57022, -57414, -57797, -58172, -58538, -58895, -59243, -59583, -59913, -60235,
//        -60547, -60850, -61144, -61429, -61705, -61971, -62228, -62475, -62714, -62942, -63162, -63371, -63571, -63762, -63943, -64115, -64276, -64428, -64571, -64703, -64826, -64939, -65043, -65136, -65220, -65294, -65358, -65412, -65457, -65491, -65516, -65531,
//        -65536, -65531, -65516, -65491, -65457, -65412, -65358, -65294, -65220, -65136, -65043, -64939, -64826, -64703, -64571, -64428, -64276, -64115, -63943, -63762, -63571, -63371, -63162, -62942, -62714, -62475, -62228, -61971, -61705, -61429, -61144, -60850,
//        -60547, -60235, -59913, -59583, -59243, -58895, -58538, -58172, -57797, -57414, -57022, -56621, -56212, -55794, -55368, -54933, -54491, -54040, -53581, -53114, -52639, -52155, -51665, -51166, -50660, -50146, -49624, -49095, -48558, -48015, -47464, -46906,
//        -46340, -45768, -45189, -44603, -44011, -43412, -42806, -42194, -41575, -40950, -40319, -39682, -39039, -38390, -37736, -37075, -36409, -35738, -35061, -34379, -33692, -32999, -32302, -31600, -30893, -30181, -29465, -28745, -28020, -27291, -26557, -25820,
//        -25079, -24334, -23586, -22833, -22078, -21319, -20557, -19792, -19024, -18253, -17479, -16702, -15923, -15142, -14359, -13573, -12785, -11995, -11204, -10410,  -9616,  -8819,  -8022,  -7223,  -6423,  -5622,  -4821,  -4018,  -3215,  -2412,  -1608,   -804,
//
//        // 512
//        /*
//        0,    804,   1608,   2412,   3215,   4018,   4821,   5622,   6423,   7223,   8022,   8819,   9616,  10410,  11204,  11995,  12785,  13573,  14359,  15142,  15923,  16702,  17479,  18253,  19024,  19792,  20557,  21319,  22078,  22833,  23586,  24334,
//        25079,  25820,  26557,  27291,  28020,  28745,  29465,  30181,  30893,  31600,  32302,  32999,  33692,  34379,  35061,  35738,  36409,  37075,  37736,  38390,  39039,  39682,  40319,  40950,  41575,  42194,  42806,  43412,  44011,  44603,  45189,  45768,
//        46340,  46906,  47464,  48015,  48558,  49095,  49624,  50146,  50660,  51166,  51665,  52155,  52639,  53114,  53581,  54040,  54491,  54933,  55368,  55794,  56212,  56621,  57022,  57414,  57797,  58172,  58538,  58895,  59243,  59583,  59913,  60235,
//        60547,  60850,  61144,  61429,  61705,  61971,  62228,  62475,  62714,  62942,  63162,  63371,  63571,  63762,  63943,  64115,  64276,  64428,  64571,  64703,  64826,  64939,  65043,  65136,  65220,  65294,  65358,  65412,  65457,  65491,  65516,  65531,
//        */
//    };
	
	public static final int  PRECISION_BITS   = 10;           // Number of PRECISION_BITS used for decimal part
    public static final long UNIT   = 1 << PRECISION_BITS;    // All operations use arithmetic where unit is 2^10 = 1024
    public static final long PI     = 3217;                   // PI << 10
    public static final int SIN_BIT10[]  =                    // Obtiene valores relativos de angulos entre 0 y 90
                                        { 0,  18,  36,  54,  71,  89, 107, 125, 143, 160,
                                        178, 195, 213, 230, 248, 265, 282, 299, 316, 333,
                                        350, 367, 384, 400, 416, 433, 449, 465, 481, 496,
                                        512, 527, 543, 558, 573, 587, 602, 616, 630, 644,
                                        658, 672, 685, 698, 711, 724, 737, 749, 761, 773,
                                        784, 796, 807, 818, 828, 839, 849, 859, 868, 878,
                                        887, 896, 904, 912, 920, 928, 935, 943, 949, 956,
                                        962, 968, 974, 979, 984, 989, 994, 998, 1002,1005,
                                        1008,1011,1014,1016,1018,1020,1022,1023,1023,1024,1024};

  
    
    //<editor-fold defaultstate="collapsed" desc=" Basic Arithmetic functions ">
   
    public static long sqrt(long number) {
        if( number == 0) return 0;
        number  = number << PRECISION_BITS;   //To compute with PRECISION_BITS PRECISION_BITS of precision

        long r = number;
        long t = 0;
        while(t != r) {
            t = r;
            r = ( (number << PRECISION_BITS)/r + r ) >> 1;
        }
        return r >> (PRECISION_BITS >> 1);

    }
    
    
    public static long pow2(long n) {
        return ( (n * n) >> PRECISION_BITS );
    }
    /*
     * Return unitary normal vector to the rect that joint posA with posB points
     * pointing to (x,y) coordenates
     */
    public static long[] normal(long pX,long pY,long qX,long qY,long x, long y) {
        //Normal vector
        long[] u = { qY-pY, pX-qX };
        if( (y-pY)*(qX-pX) > (x-pX)*(qY-pY) ) {
            u[0] = pY-qY;
            u[1] = qX-pX;
        }
        //Make unitary
        u       = Math2D.normalize(u[0], u[1]);
        //u[0]    = u[0]>>5;
        //u[1]    = u[1]>>5;
        return u;
    }
       
    /**
     * Return unitary vector asociated with v=(x1,y1)
     */
    public static long[] normalize(long x1, long y1) {
        long v[]    = {0,0};
        x1 = x1>>PRECISION_BITS;
        y1 = y1>>PRECISION_BITS;        
        v[1]        = sqrt( (x1*x1 + y1*y1) >> PRECISION_BITS );
        if( v[1] == 0 ) {
            v[0] = x1;
            v[1] = y1;
            return v;
        }
        v[0]    = (x1 << PRECISION_BITS) / v[1];
        v[1]    = (y1 << PRECISION_BITS) / v[1];
        return v;
    }
    
     /**
     * Calculate the point C=(x3,y3) proyection over the rect defined by P1=(x1,y1) and P2=(x2,y2)
     */
    public static long[] getPointOverRect(long x3, long y3, long x1, long y1, long x2, long y2) {
        long pQ[] = {0,0};
        //Cases
        if( Math.abs(x1-x2) < UNIT ) {
            pQ[0] = x1;
            pQ[1] = y3;
        } else if( Math.abs(y1-y2) < UNIT ) {
            pQ[0] = x3;
            pQ[1] = y1;
        } else {
            long m  = ((y2-y1) << PRECISION_BITS) / (x2-x1);
            if(m!=0) {
                long num= (x3 << PRECISION_BITS) / m;
                num    += ((m*x1) >> PRECISION_BITS);
                num    += y3 - y1;
                num     = ((m*num) >> PRECISION_BITS);
                pQ[0]   = (num << PRECISION_BITS) / ( ((m*m) >> PRECISION_BITS) + UNIT );
                pQ[1]   = y1 + ((m*(pQ[0]-x1)) >> PRECISION_BITS);
            }
            else {
                pQ[0] = x3;
                pQ[1] = y1;
            }
        }
        return pQ;
    }
    
    /**
     * Calculate the relative position of the point (x3,y3) and the rect defined by (x1,y1) and (x2,y2)
     * Return:
     *  +1: if the point is OVER the rect
     *   0: if the point is IN the rect or the rect is parallel to Y-axis
     *  -1: if the point is BELOW the rect
     */
    public static int getRelativePosition(long x3, long y3, long x1, long y1, long x2, long y2) {
        if( x1 == x2 ) {
            return 0;
        } else {
            long p[] = getPointOverRect(x3, y3, x1, y1, x2, y2);
            if( y3 > p[1] ) {
                return 1;
            } else if( y3 < p[1] ) {
                return -1;
            } else {
                return 0;
            }
        }
    }    
    
    public static int getRelativePosition2(long x3, long y3, long x1, long y1, long x2, long y2) {
        if( x1 >= x2-3000) {
            return 0;
        } else {
            long p[] = getPointOverRect(x3, y3, x1, y1, x2, y2);
            if( y3 > p[1] ) {
                return 1;
            } else if( y3 < p[1] ) {
                return -1;
            } else {
                return 0;
            }
        }
    }    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Rotate & Trigonometric functions ">
    /**
     * Return the new coordinates for a point P=(x1,y1) rotate "angle" grades regarding origin point O=(0,0)
     * Note that angle must be in sexagesimal grades
     */
    public static long[] rotatePoint(long x1, long y1, int angle) {
        long pos[] = {0,0};
        //Aply formula:
        //  x2 = x1*cos(a) - y1*sen(a)
        //  y2 = y1*cos(a) + x1*sen(a)
        pos[0] = ( x1*cos(angle) - y1*sin(angle) ) >> PRECISION_BITS;
        pos[1] = ( y1*cos(angle) + x1*sin(angle) ) >> PRECISION_BITS;
        //System.out.println("rotatePoint("+x1+","+y1+","+angle+")= {"+pos[0]+","+pos[1]+"}");
        return pos;
    }
    public static long[] rotatePoint(long x1, long y1, int angle,long xC, long yC) {
        long pos[] = {0,0};
        //Aply formula:
        //  x2 = x1*cos(a) - y1*sen(a)
        //  y2 = y1*cos(a) + x1*sen(a)
        pos[0] = ( (x1-xC)*cos(angle) - (y1-yC)*sin(angle) ) >> PRECISION_BITS;
        pos[1] = ( (y1-yC)*cos(angle) + (x1-xC)*sin(angle) ) >> PRECISION_BITS;
        //Systmout.println("rotatePoint("+x1+","+y1+","+angle+")= {"+pos[0]+","+pos[1]+"}");
        pos[0]+=xC;
        pos[1]+=yC;
        return pos;
    }
    
    public static int sin(int angle) {
        if( 0 <= angle && angle <= 90 ) {
            return SIN_BIT10[angle];
        } else if( -90 <= angle && angle < 0 ) {
            return -SIN_BIT10[-angle];
        } else if( 90 < angle && angle <= 180 ) {
            return SIN_BIT10[180-angle];
        } else if( -180 <= angle && angle < -90 ) {
            return -SIN_BIT10[180+angle];
        } else {
            /*
            if( angle > 0 ) {
                return -sin(angle%180);
            } else {
                return sin((-angle)%180);
             *
            }
            */
            if( angle > 180 ) {
                while( angle > 180 ) angle -= 360;
            } else if( angle < 180 ) {
                while( angle < 0 ) angle += 360;
            } else {
                System.out.println("CAN'T COMPUTE sin("+angle+")");
            }
            return sin(angle);
        }
    }
    public static int cos(int angle) {
        return sin(90+angle);
    }
    
    /*
     * Return angle between x-axis and the rect that join points P=(x1,y1) and Q=(x2,y2)
     * Note that tang(angle) = M = (y2-y1)/(x2-x1)
     */
    public static int atan(long x1, long y1, long x2, long y2) {
        long x      = x2 - x1;
        long y      = y2 - y1;
        long coeff_1= PI / 4;
        long coeff_2= 3 * coeff_1;
        long abs_y   = Math.abs(y);
        long angle;
        
        if(x == 0) {
            return 90;
        } else if(y == 0) {
            return 0;
        } else if(x > 0) {
            long r  = (x-abs_y) << PRECISION_BITS;
            r       = r / (x+abs_y);
            angle   = coeff_1 - coeff_1 * r;
        } else {
            long r  = (x+abs_y) << PRECISION_BITS;
            r       = r / (abs_y - x);
            angle   = coeff_2 - coeff_1 * r;
        }
        //From radians to sexagesimal degrees
        angle = ( (angle * 180)/PI ) >> PRECISION_BITS;
        if( x >= 0 ) {
            return y < 0 ? (int)(-angle - 45) : (int)(angle + 45);
        } else {
            return y < 0 ? (int)(-angle + 45) : (int)(angle - 45);
        }
    }
    
    public static int atan2(long x1, long y1, long x2, long y2) {
        long x      = x2 - x1;
        long y      = y2 - y1;
        long coeff_1= PI >>2;
        long coeff_2= 3 * coeff_1;
        long abs_y   = Math.abs(y);
        long angle;
        
        if(x<0)
            x=Math.abs(x);
        
        if(x == 0) {
            return 90;
        } else if(y == 0) {
            return 0;
        } else if(x > 0) {
            long r  = (x-abs_y) << PRECISION_BITS;
            r       = r / (x+abs_y);
            angle   = coeff_1 - coeff_1 * r;
        } else {
            long r  = (x+abs_y) << PRECISION_BITS;
            r       = r / (abs_y - x);
            angle   = coeff_2 - coeff_1 * r;
        }
        //From radians to sexagesimal degrees
        angle = ( (angle * 180)/PI ) >> PRECISION_BITS;
        if( x >= 0 ) {
            return y < 0 ? (int)(-angle - 45) : (int)(angle + 45);
        } else {
            return y < 0 ? (int)(-angle + 45) : (int)(angle - 45);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Distances 2D ">
    /**
     * Return the distance^2 between point O=(x1,y1) and the rect defined by P=(x2,y2) and Q=(x3,y3)
     */
    public static long distancePointRect_Pow2(long x1, long y1, long x2, long y2, long x3, long y3) {
        long d  = 0;
        //Cases
        if( Math.abs(x2-x3) < UNIT ) {
            d  = x2-x1;
            d  = (d*d) >> PRECISION_BITS;
        } else if( Math.abs(y2-y3) < UNIT ) {
            d  = y2-y1;
            d  = (d*d) >> PRECISION_BITS;
        } else {
            //Calculate coeficients of the rect r: Ax + By + C = 0
            // A = (y3-y2)/(x3-x2); B = -1; C = y2-Ax2
            // d(O,r) = (Ax1-y1+y2-Ax2)/sqrt(A^2+1);                      
            long m  = ((y3-y2) << PRECISION_BITS) / (x3-x2);
            long num= ( m*(x1-x2) ) >> PRECISION_BITS;
            num     = num - y1 + y2;
            num     = (num*num) >> PRECISION_BITS;
            long den= (m*m) >> PRECISION_BITS;
            den     = den + UNIT;
            d       = (num << PRECISION_BITS) / den;              
            /*
            long m  = ((y3-y2) << PRECISION_BITS) / (x3-x2);
            d  = ((m*x1 - m*x2) >> PRECISION_BITS) - y1 + y2;
            d  = (d*d) >> PRECISION_BITS;
            d  = d / ( ((m*m) >> PRECISION_BITS) + UNIT );
            */
        }
        //Return distance powered by 2
        return d;
    }
    
    /**
     * Return the distance^2 between points P=(x1,y1) and Q=(x2,y2)
     */
    public static long distancePointPoint_Pow2(long x1, long y1, long x2, long y2) {
        long d = 0;
        //Cases
        if( Math.abs(x2-x1) < UNIT ) {
            d  = y2-y1;
            d  = (d*d) >> PRECISION_BITS;
        } else if( Math.abs(y2-y1) < UNIT ) {
            d  = x2-x1;
            d  = (d*d) >> PRECISION_BITS;
        } else {
            d  = ( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) ) >> PRECISION_BITS;
        }
        //Return distance powered by 2
        return d;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Line & Segments functions ">
    /**
     * Check if the rect that joins (x1,y1) with (x2,y2) cut the rect that joins (x3,y3) with (x4,y4)
     * and return the intersection point in pQ variable if proceed
     * Note if the two rects are the same this function will return TRUE but pQ will value NULL
     */
    public static boolean intersectionPointRects(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4, long[] pQ) {
        long Ma = ((y2-y1) << PRECISION_BITS) / (x2-x1);
        long Mb = ((y4-y3) << PRECISION_BITS) / (x4-x3);
        
        //Two rects are parallel if have the same slope
        if( Ma == Mb ) {
            if( y3-y1 == Ma*(x3-x1) ) {
                pQ = null;
                return true;    //The two rects are the same
            } else {
                return false;
            }
        }
        
        if( pQ != null ) {
            //Calculate the value of x component of intersection point
            pQ[0] = y3 - y1 + ( Ma*x1 >> PRECISION_BITS ) - ( Mb*x3 >> PRECISION_BITS );
            pQ[0] = (pQ[0] << PRECISION_BITS) / ( Ma - Mb );

            //Calculate the value of y component of intersection point
            pQ[1] = ( Ma*(pQ[0] - x1) >> PRECISION_BITS ) + y1;
        }
        
        return true;
    }
    /**
     * Check if the segment that joins (x1,y1) with (x2,y2) cut the segment that joins (x3,y3) with (x4,y4)
     * and return the intersection point in pQ variable if proceed
     */
    public static boolean intersectionPointSegments(long x1, long y1, long x2, long y2, long x3, long y3, long x4, long y4, long[] pQ) {
        if( intersectionPointRects(x1, y1, x2, y2, x3, y3, x4, y4, pQ) ) {
            //Fast check if intersection point pQ are over both segments
            if( Math.min(x1,x2) <= pQ[0] && pQ[0] <= Math.max(x1,x2) &&
                Math.min(y1,y2) <= pQ[1] && pQ[1] <= Math.max(y1,y2) &&
                Math.min(x3,x4) <= pQ[0] && pQ[0] <= Math.max(x3,x4) &&
                Math.min(y3,y4) <= pQ[1] && pQ[1] <= Math.max(y3,y4)    ) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if the segment that joins (sx1,sy1) with (sx2, sy2) intercept the polygon defined by the list of points (px[],py[])
     * Return the index of the polygon side that intercept the segment and the intersection point in pQ variable
     * Return -1 if no colission between polygon and segment
     */
    public static int intersectionPolygonSegment(long[] px, long[] py, long sx1, long sy1, long sx2, long sy2, long[] pQ) {
        for(int s = 0 ; s < px.length-1 ; s++) {
            if( intersectionPointSegments(px[s],py[s],px[s+1],py[s+1],sx1,sy1,sx2,sy2,pQ) ) {
                return s;
            }
        }
        if( intersectionPointSegments(px[px.length-1],py[px.length-1],px[0],py[0],sx1,sy1,sx2,sy2,pQ) ) {
            return px.length-1;
        }
        return -1;
    }
    //</editor-fold>
    
    
    
    public static int getPixel(long c)
    {
        return (int)c>>PRECISION_BITS;
    }
    public static long getWorld(long c)
    {
        return (long)c >> PRECISION_BITS;
    }
    
    /*
     * _iPosX1 = init objetc pos X
     * _iPosY1 = init objetc pos Y
     * _iPosX2 = final objetc pos X
     * _iPosY2 = final objetc pos Y
     */
    public static int getAngle360(long _iPosX1, long _iPosY1, long _iPosX2, long _iPosY2) {
       int angle360 = atan(_iPosX1, _iPosY1, _iPosX2, _iPosY2);
       angle360 = angle360 < 0?angle360*-1:angle360;
       //System.out.println("angle360: "+angle360);
       if(_iPosX2 ==_iPosX1){
          if(_iPosY2 == _iPosY1) return -1;
          else if (_iPosY2 > _iPosY1) return 270;
          else return 90;
       }
       else if(_iPosY2 ==_iPosY1){
          if(_iPosX2 == _iPosX1) return -1;
          else if (_iPosX2 > _iPosX1) return 0;
          else return 180;
       }
       
       if(_iPosX2 >_iPosX1 && _iPosY2 < _iPosY1) 
          return angle360;
       //90-180
       else if(_iPosX2 <_iPosX1 && _iPosY2 < _iPosY1) 
          return 180 - angle360;
       //180-270
       else if(_iPosX2 <_iPosX1 && _iPosY2 > _iPosY1) 
          return 180 + angle360;
       //270-360
       else 
          return 360 - angle360;
      
       }
    
    //Obtiene la diferencia de dos angulos en un rango de 0-360
    public static int getDif360(int _iAng1, int _iAng2){
        if(_iAng1==_iAng2)
            return 0;
        int ang = (_iAng1% 360) - (_iAng2% 360) % 360;
        int diff = ang < 1 ? 360 + ang : ang;
        return diff > 180 ? 360 - diff : diff;
    }
    
    public static final float CIRCLE = (float) (2 * Math.PI);
    public static final float MID_CIR = CIRCLE / 2;
    public static final float QUA_CIR = CIRCLE / 4;
    public static final float EIG_CIR = CIRCLE / 8;
    private static final float angPrec = CIRCLE / 3600;

    public static float atan(float tanValue) {
        float absTan = Math.abs(tanValue);
        float angle, lowLimit = 0, highLimit = QUA_CIR;
        do {
            angle = (lowLimit + highLimit) / 2;
            if (Math.tan(angle) < absTan) {
                lowLimit = angle;
            } else {
                highLimit = angle;
            }
        } while (highLimit - lowLimit > angPrec);
        return lowLimit * (tanValue < 0 ? -1 : 1);
    }

    public static float atan2(float sin, float cos) {
        if (cos == 0 && sin == 0) {
            return 0;
        }
        if (cos == 0) {
            return sin > 0 ? QUA_CIR : 3 * QUA_CIR;
        }
        if (sin == 0) {
            return cos > 0 ? 0 : MID_CIR;
        }
        float angle = atan(sin / cos);
        if ((sin > 0) == (cos > 0)) {
            return angle + (sin > 0 ? 0 : MID_CIR);
        } else {
            return CIRCLE + angle - (sin < 0 ? 0 : MID_CIR);
        }
   }
    
    
    /**
	 * Redondea cualquier valor al valor mas cercano potencia de 2.
	 * Mantiene el signo.
	 * @param n
	 * @return
	 */
	public static int adjustPow2(float n){
		int origin = (int)Math.abs(n);
		int na = origin;
		
		//Obtengo el numero de veces que el valor es divisible entre 2
		int p = 0;
		while(na > 0){
			na = na/2;
			p++;
		}
		
		
		//En obtengo el valor de la potencia, el de la pontencia -1 y el de la potencia +1.
		//Luego encuentro cual de estos tres valores esta mas cerca del valor original.
		int currentP = Math.abs(origin-(int)((Math.pow(2,p))));
		int currentL = Math.abs(origin- (int)(Math.pow(2,(p-1))));
		int currentM = Math.abs(origin- (int)(Math.pow(2,(p+1))));
		
		int r = -1;
		if(currentP < currentL && currentP < currentM){
			r = (int)Math.pow(2,p);
		}else if(currentL < currentM && currentL < currentM){
			r = (int)Math.pow(2,(p-1));
		}else{
			r = (int)Math.pow(2,(p+1));
		}
		
		if(n < 0){
			r = r*-1;
		}
		
		return r;
	}
	
	/**
	 * A partir del tamaño definido de un tile, devuelve el multiplo del valos mas cercano
	 * @param value
	 * @return
	 */
	public static int adjutsTileSize(float tileSize, float value){
		int d = (int)(value / tileSize);
		return d * (int)tileSize;
	}
	
	/**
	 * Redondea cualquier valor al valor (n) al numero redondo mas cercano a la division (div)
	 * Mantiene el signo.
	 * @param n
	 * @return
	 */
	public static float adjustDiv(float n, float div){
		int na = (int)Math.abs(n);
		
		na = (int)(na/div);
		
		
		//En obtengo el valor de la potencia, el de la pontencia -1 y el de la potencia +1.
		//Luego encuentro cual de estos tres valores esta mas cerca del valor original.
		float currentP = Math.abs(Math.abs(n)-(div*na));
		float currentL = Math.abs(Math.abs(n)-(div*(na-1)));
		float currentM = Math.abs(Math.abs(n)-(div*(na+1)));
		
		float r = -1;
		if(currentP < currentL && currentP < currentM){
			r = na*div;
		}else if(currentL < currentM && currentL < currentM){
			r = (na-1)*div;
		}else{
			r = (na+1)*div;
		}
		
		if(n < 0){
			r = r*-1;
		}
		
		return r;
	}
    

}
