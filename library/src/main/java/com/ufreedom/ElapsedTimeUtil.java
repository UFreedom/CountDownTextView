package com.ufreedom;

/**
 * Author UFreedom
 * 
 */
public enum  ElapsedTimeUtil  {
    
    MILLISECONDS {
        public long toSeconds(long d) { return d % C4 % C3 % C2 / C1; }
        public long toMinutes(long d) { return d % C4 % C3 / C2; }
        public long toHours(long d)   { return d % C4 / C3; }
        public long toDays(long d)    { return d / C4; }
    };


    // Handy constants for conversion methods
    static final long C1 = 1000L;
    static final long C2 = C1 * 60L;
    static final long C3 = C2 * 60L;
    static final long C4 = C3 * 24L;



    /**
     * @param duration the duration
     * @return the converted duration,
     */
    public long toSeconds(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * @param duration the duration
     * @return the converted duration,
     */
    public long toMinutes(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * @param duration the duration
     * @return the converted duration,
     */
    public long toHours(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * @param duration the duration
     * @return the converted duration,
     */
    public long toDays(long duration) {
        throw new AbstractMethodError();
    }


}
