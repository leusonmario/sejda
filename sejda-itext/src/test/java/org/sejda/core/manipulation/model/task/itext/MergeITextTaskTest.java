/*
 * Created on 16/ago/2011
 * Copyright 2011 by Andrea Vacondio (andrea.vacondio@gmail.com).
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
package org.sejda.core.manipulation.model.task.itext;

import org.sejda.core.manipulation.model.parameter.MergeParameters;
import org.sejda.core.manipulation.model.task.Task;
import org.sejda.core.manipulation.service.MergeTaskTest;

/**
 * @author Andrea Vacondio
 * 
 */
public class MergeITextTaskTest extends MergeTaskTest {

    public Task<MergeParameters> getTask() {
        return new MergeTask();
    }

}