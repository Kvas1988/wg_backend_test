package com.example.wg_backend.service;

// import org.apache.commons.lang3.builder.EqualsBuilder;
// import org.apache.commons.lang3.builder.HashCodeBuilder;
// import org.apache.commons.lang3.builder.ToStringBuilder;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Created by Ergin
 **/
public class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -25822477129613575L;

    private long limit;
    private long offset;
    private Sort sort;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   can be {@literal null}.
     */
    public OffsetBasedPageRequest(long offset, long limit, Sort sort) {
        this.limit = checkLimit(limit);
        this.offset = checkOffset(offset);
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */

    // public OffsetBasedPageRequest(int offset, int limit, String properties, Sort.Direction direction) {
    //     this(offset, limit, Sort.by(direction, properties));
    // }

    public OffsetBasedPageRequest(long offset, long limit, Sort.Direction direction, String properties) {
        this.limit = checkLimit(limit);
        this.offset = checkOffset(offset);
        this.sort = Sort.by(direction, properties);
    }

    public OffsetBasedPageRequest(String offset, String limit, String properties, String direction) {
        System.out.println(offset);

        Sort.Direction dir = switch (direction) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> Sort.DEFAULT_DIRECTION;
        };
        this.sort = Sort.by(dir, properties);
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    public OffsetBasedPageRequest(long offset, long limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return (int) limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious() ? new OffsetBasedPageRequest(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }


    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    private long checkOffset(long offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        return offset;
    }

    private long checkLimit(long limit) {
        if (limit == 0) {
            // limit = Long.MAX_VALUE;
            limit = 2000;
        } else if (limit < 0) {
            throw new IllegalArgumentException("Limit must not be less than zero!");
        }
        return limit;
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //
    //     if (!(o instanceof OffsetBasedPageRequest)) return false;
    //
    //     OffsetBasedPageRequest that = (OffsetBasedPageRequest) o;
    //
    //     return new EqualsBuilder()
    //             .append(limit, that.limit)
    //             .append(offset, that.offset)
    //             .append(sort, that.sort)
    //             .isEquals();
    // }
    //
    // @Override
    // public int hashCode() {
    //     return new HashCodeBuilder(17, 37)
    //             .append(limit)
    //             .append(offset)
    //             .append(sort)
    //             .toHashCode();
    // }
    //
    // @Override
    // public String toString() {
    //     return new ToStringBuilder(this)
    //             .append("limit", limit)
    //             .append("offset", offset)
    //             .append("sort", sort)
    //             .toString();
    // }
}