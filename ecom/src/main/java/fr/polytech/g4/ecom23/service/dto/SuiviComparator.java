package fr.polytech.g4.ecom23.service.dto;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class SuiviComparator implements Comparator<SuividonneesDTO> {

    @Override
    public int compare(SuividonneesDTO s1, SuividonneesDTO s2) {
        if (s1.getDate().isEqual(s2.getDate()))
            return 0;
        if (s1.getDate().isBefore(s2.getDate()))
            return 1;
        return -1;
    }

    @Override
    public Comparator<SuividonneesDTO> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<SuividonneesDTO> thenComparing(Comparator<? super SuividonneesDTO> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<SuividonneesDTO> thenComparing(Function<? super SuividonneesDTO, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<SuividonneesDTO> thenComparing(Function<? super SuividonneesDTO, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<SuividonneesDTO> thenComparingInt(ToIntFunction<? super SuividonneesDTO> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<SuividonneesDTO> thenComparingLong(ToLongFunction<? super SuividonneesDTO> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<SuividonneesDTO> thenComparingDouble(ToDoubleFunction<? super SuividonneesDTO> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }
}
