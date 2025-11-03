package com.ewersson.dslist.dto;

import java.util.Objects;

public class ReplacementDTO {

    private int sourceIndex;
    private int destinationIndex;

    public ReplacementDTO(){}

    public ReplacementDTO(int sourceIndex, int destinationIndex) {
        this.sourceIndex = sourceIndex;
        this.destinationIndex = destinationIndex;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReplacementDTO that = (ReplacementDTO) o;
        return sourceIndex == that.sourceIndex && destinationIndex == that.destinationIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceIndex, destinationIndex);
    }
}
