/*
 * Copyright 2015 Adam Bien.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.airhacks.breakr;

/*
 * #%L
 * breakr
 * %%
 * Copyright (C) 2015 Adam Bien
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;

/**
 *
 * @author airhacks.com
 */
public class Circuit {

    protected AtomicLong failureCounter;

    @PostConstruct
    public void init() {
        this.failureCounter = new AtomicLong(0);
    }

    public void newException(Exception ex) {
        this.failureCounter.incrementAndGet();
    }

    public void newTimeout(long duration) {
        this.failureCounter.incrementAndGet();
    }

    public boolean isOpen(long maxFailures) {
        return (this.failureCounter.get() > maxFailures);
    }

    public void onFailureCounterValue(@Observes @FailureCounterValue long value) {
        failureCounter.set(value);
    }

}