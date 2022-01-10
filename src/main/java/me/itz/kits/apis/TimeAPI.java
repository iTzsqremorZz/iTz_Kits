package me.itz.kits.apis;

public enum TimeAPI {

    SECOND("SEGUNDOS", 1 / 60), MINUTE("MINUTOS", 1), HOUR("HORAS", 60), DAY("DIAS", 60 * 24), WEEK("SEMANAS",
            60 * 24 * 7), MONTH("MESES", 30 * 60 * 24), YEAR("ANOS", 30 * 60 * 24 * 12);

    public String name;
    public int multi;

    TimeAPI(String name, int multi) {
        this.name = name;
        this.multi = multi;
    }

    public static long getTicks(String un, int time) {
        long sec;

        try {
            sec = time * 60;
        } catch (NumberFormatException ex) {
            return 0;
        }

        for (TimeAPI unit : TimeAPI.values()) {
            if (un.startsWith(unit.name)) {
                return (sec *= unit.multi) * 1000;
            }
        }

        return 0;
    }

    public static String getMSG(long endOfBan) {
        String message = "";

        long now = System.currentTimeMillis();
        long diff = endOfBan - now;
        int seconds = (int) (diff / 1000);

        if (seconds >= 60 * 60 * 24) {
            int days = seconds / (60 * 60 * 24);
            seconds = seconds % (60 * 60 * 24);

            if (days > 1) {
                message += days + "d ";
            } else {
                message += days + "d ";
            }
        }
        if (seconds >= 60 * 60) {
            int hours = seconds / (60 * 60);
            seconds = seconds % (60 * 60);

            if (hours > 1) {
                message += hours + "h ";
            } else {
                message += hours + "h ";
            }
        }
        if (seconds >= 60) {
            int min = seconds / 60;
            seconds = seconds % 60;

            if (min > 1) {
                message += min + "m ";
            } else {
                message += min + "m ";
            }
        }
        if (seconds >= 0) {
            message += seconds + "s ";
        }

        return message;
    }

}
