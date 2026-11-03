package projectc.mod.civilization.calendar;

import net.minecraft.server.level.ServerLevel;

public final class CivilizationCalendar {

    public static final int DAYS_PER_MONTH = 4;
    public static final int MONTHS_PER_YEAR = 12;
    public static final int DAYS_PER_YEAR =
            DAYS_PER_MONTH * MONTHS_PER_YEAR;

    private static final String[] MONTH_NAMES = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    private CivilizationCalendar() {
    }

    public static CalendarDate fromAbsoluteDay(long absoluteDay) {
        if (absoluteDay < 0) {
            throw new IllegalArgumentException(
                    "Absolute day cannot be negative"
            );
        }

        long year = absoluteDay / DAYS_PER_YEAR + 1;

        int monthIndex = (int) (
                (absoluteDay % DAYS_PER_YEAR) / DAYS_PER_MONTH
        );

        int dayOfMonth = (int) (
                absoluteDay % DAYS_PER_MONTH
        ) + 1;

        return new CalendarDate(
                absoluteDay,
                dayOfMonth,
                monthIndex + 1,
                year,
                MONTH_NAMES[monthIndex],
                getSeason(monthIndex + 1)
        );
    }

    private static Season getSeason(int month) {
        return switch (month) {
            case 3, 4, 5 -> Season.SPRING;
            case 6, 7, 8 -> Season.SUMMER;
            case 9, 10, 11 -> Season.AUTUMN;
            default -> Season.WINTER;
        };
    }

    public enum Season {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }

    public record CalendarDate(
            long absoluteDay,
            int dayOfMonth,
            int month,
            long year,
            String monthName,
            Season season
    ) {
        @Override
        public String toString() {
            return dayOfMonth + " " + monthName
                    + ", Year " + year
                    + " (" + season + ")";
        }
    }

    public static CalendarDate fromOverworldClockTime(long clockTicks) {
        if (clockTicks < 0) {
            throw new IllegalArgumentException("Clock ticks cannot be negative");
        }

        long absoluteDay = clockTicks / 24_000L;
        return fromAbsoluteDay(absoluteDay);
    }
    public static CalendarDate getCurrentDate(ServerLevel level) {
        return fromOverworldClockTime(level.getOverworldClockTime());
    }
}
