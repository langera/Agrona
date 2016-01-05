/*
 * Copyright 2014 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.real_logic.agrona.concurrent.broadcast;

import uk.co.real_logic.agrona.BitUtil;

/**
 * Layout of the broadcast buffer. The buffer consists of a ring of messages that is a power of 2 in size.
 * This is followed by a trailer section containing state information about the ring.
 */
public class BroadcastBufferDescriptor
{
    /** Offset within the trailer for where the tail intended value is stored. */
    public static final long TAIL_INTENT_COUNTER_OFFSET;

    /** Offset within the trailer for where the tail value is stored. */
    public static final long TAIL_COUNTER_OFFSET;

    /** Offset within the trailer for where the latest sequence value is stored. */
    public static final long LATEST_COUNTER_OFFSET;

    /** Total size of the trailer */
    public static final long TRAILER_LENGTH;

    static
    {
        int offset = 0;
        TAIL_INTENT_COUNTER_OFFSET = offset;

        offset += BitUtil.SIZE_OF_LONG;
        TAIL_COUNTER_OFFSET = offset;

        offset += BitUtil.SIZE_OF_LONG;
        LATEST_COUNTER_OFFSET = offset;

        TRAILER_LENGTH = BitUtil.CACHE_LINE_LENGTH * 2;
    }

    /**
     * Check the the buffer capacity is the correct size.
     *
     * @param capacity to be checked.
     * @throws IllegalStateException if the buffer capacity is not a power of 2.
     */
    public static void checkCapacity(final long capacity)
    {
        if (!BitUtil.isPowerOfTwo(capacity))
        {
            final String msg = "Capacity must be a positive power of 2 + TRAILER_LENGTH: capacity=" + capacity;
            throw new IllegalStateException(msg);
        }
    }
}
