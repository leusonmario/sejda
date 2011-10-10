/*
 * Created on 26/ago/2011
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
package org.sejda.core.manipulation.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.sejda.core.TestUtils;
import org.sejda.core.exception.TaskException;
import org.sejda.core.manipulation.model.input.PdfStreamSource;
import org.sejda.core.manipulation.model.parameter.ExtractPagesParameters;
import org.sejda.core.manipulation.model.pdf.PdfVersion;
import org.sejda.core.manipulation.model.pdf.page.PageRange;
import org.sejda.core.manipulation.model.pdf.page.PredefinedSetOfPages;
import org.sejda.core.manipulation.model.task.Task;

import com.lowagie.text.pdf.PdfReader;

/**
 * Test for an extract pages task.
 * 
 * @author Andrea Vacondio
 * 
 */
public abstract class ExtractPagesTaskTest extends PdfOutEnabledTest implements TestableTask<ExtractPagesParameters> {

    private DefaultTaskExecutionService victim = new DefaultTaskExecutionService();

    private TaskExecutionContext context = mock(DefaultTaskExecutionContext.class);
    private ExtractPagesParameters parameters;

    @Before
    public void setUp() {
        TestUtils.setProperty(victim, "context", context);
    }

    private void setUpParametersOddPages() {
        parameters = new ExtractPagesParameters(PredefinedSetOfPages.ODD_PAGES);
        parameters.setOverwrite(true);
        parameters.setCompress(true);
        parameters.setVersion(PdfVersion.VERSION_1_6);
        parameters.setSource(PdfStreamSource.newInstanceNoPassword(
                getClass().getClassLoader().getResourceAsStream("pdf/test_file.pdf"), "test.pdf"));
    }

    private void setUpParametersPageRangesPages() {
        PageRange ristRange = new PageRange(1, 1);
        PageRange secondRange = new PageRange(3);
        Set<PageRange> ranges = new HashSet<PageRange>();
        ranges.add(ristRange);
        ranges.add(secondRange);
        parameters = new ExtractPagesParameters(ranges);
        parameters.setOverwrite(true);
        parameters.setCompress(true);
        parameters.setVersion(PdfVersion.VERSION_1_6);
        parameters.setSource(PdfStreamSource.newInstanceNoPassword(
                getClass().getClassLoader().getResourceAsStream("pdf/test_file.pdf"), "test.pdf"));
    }

    private void setUpParametersPageRangesMediumFile() {
        PageRange ristRange = new PageRange(2, 20);
        Set<PageRange> ranges = new HashSet<PageRange>();
        ranges.add(ristRange);
        parameters = new ExtractPagesParameters(ranges);
        parameters.setOverwrite(true);
        parameters.setCompress(true);
        parameters.setVersion(PdfVersion.VERSION_1_6);
        parameters.setSource(PdfStreamSource.newInstanceNoPassword(
                getClass().getClassLoader().getResourceAsStream("pdf/medium_test.pdf"), "test.pdf"));
    }

    @Test
    public void testExecuteExtractOddPages() throws TaskException, IOException {
        setUpParametersOddPages();
        when(context.getTask(parameters)).thenReturn((Task) getTask());
        initializeNewFileOutput(parameters);
        victim.execute(parameters);
        PdfReader reader = null;
        try {
            reader = getReaderFromResultFile();
            assertCreator(reader);
            assertVersion(reader, PdfVersion.VERSION_1_6);
            assertEquals(2, reader.getNumberOfPages());
        } finally {
            nullSafeCloseReader(reader);
        }
    }

    @Test
    public void testExecuteExtractRanges() throws TaskException, IOException {
        setUpParametersPageRangesPages();
        when(context.getTask(parameters)).thenReturn((Task) getTask());
        initializeNewFileOutput(parameters);
        victim.execute(parameters);
        PdfReader reader = null;
        try {
            reader = getReaderFromResultFile();
            assertCreator(reader);
            assertVersion(reader, PdfVersion.VERSION_1_6);
            assertEquals(3, reader.getNumberOfPages());
        } finally {
            nullSafeCloseReader(reader);
        }
    }

    @Test
    public void testExecuteExtractRangesMedium() throws TaskException, IOException {
        setUpParametersPageRangesMediumFile();
        when(context.getTask(parameters)).thenReturn((Task) getTask());
        initializeNewFileOutput(parameters);
        victim.execute(parameters);
        PdfReader reader = null;
        try {
            reader = getReaderFromResultFile();
            assertCreator(reader);
            assertVersion(reader, PdfVersion.VERSION_1_6);
            assertEquals(19, reader.getNumberOfPages());
        } finally {
            nullSafeCloseReader(reader);
        }
    }
}
