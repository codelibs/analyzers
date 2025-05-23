/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.analysis.en;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class FlexiblePorterStemmerTest {
    @Test
    public void testAll() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer();
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consign", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consist", stemmer.stem("consistency"));
        Assert.assertEquals("consist", stemmer.stem("consistent"));
        Assert.assertEquals("consist", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consol", stemmer.stem("consolation"));
        Assert.assertEquals("consol", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("consol", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("consol", stemmer.stem("consoles"));
        Assert.assertEquals("consolid", stemmer.stem("consolidate"));
        Assert.assertEquals("consolid", stemmer.stem("consolidated"));
        Assert.assertEquals("consolid", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("conson", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspir", stemmer.stem("conspirator"));
        Assert.assertEquals("conspir", stemmer.stem("conspirators"));
        Assert.assertEquals("conspir", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constabl", stemmer.stem("constable"));
        Assert.assertEquals("constabl", stemmer.stem("constables"));
        Assert.assertEquals("constanc", stemmer.stem("constance"));
        Assert.assertEquals("constanc", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep1() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, false, false, false, false, false);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consignment", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consistency", stemmer.stem("consistency"));
        Assert.assertEquals("consistent", stemmer.stem("consistent"));
        Assert.assertEquals("consistently", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consolation", stemmer.stem("consolation"));
        Assert.assertEquals("consolation", stemmer.stem("consolations"));
        Assert.assertEquals("consolatory", stemmer.stem("consolatory"));
        Assert.assertEquals("console", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("console", stemmer.stem("consoles"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidate"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidated"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingly", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("consonant", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicuou", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicuously", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiracy", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspirator", stemmer.stem("conspirator"));
        Assert.assertEquals("conspirator", stemmer.stem("conspirators"));
        Assert.assertEquals("conspire", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constable", stemmer.stem("constable"));
        Assert.assertEquals("constable", stemmer.stem("constables"));
        Assert.assertEquals("constance", stemmer.stem("constance"));
        Assert.assertEquals("constancy", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightly", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep2() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, true, false, false, false, false);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consignment", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consistenci", stemmer.stem("consistency"));
        Assert.assertEquals("consistent", stemmer.stem("consistent"));
        Assert.assertEquals("consistentli", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consolation", stemmer.stem("consolation"));
        Assert.assertEquals("consolation", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("console", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("console", stemmer.stem("consoles"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidate"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidated"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("consonant", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicuou", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicuousli", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspirator", stemmer.stem("conspirator"));
        Assert.assertEquals("conspirator", stemmer.stem("conspirators"));
        Assert.assertEquals("conspire", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constable", stemmer.stem("constable"));
        Assert.assertEquals("constable", stemmer.stem("constables"));
        Assert.assertEquals("constance", stemmer.stem("constance"));
        Assert.assertEquals("constanci", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep3() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, true, true, false, false, false);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consignment", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consistence", stemmer.stem("consistency"));
        Assert.assertEquals("consistent", stemmer.stem("consistent"));
        Assert.assertEquals("consistent", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consolate", stemmer.stem("consolation"));
        Assert.assertEquals("consolate", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("console", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("console", stemmer.stem("consoles"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidate"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidated"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("consonant", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicuou", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicuous", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspirate", stemmer.stem("conspirator"));
        Assert.assertEquals("conspirate", stemmer.stem("conspirators"));
        Assert.assertEquals("conspire", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constable", stemmer.stem("constable"));
        Assert.assertEquals("constable", stemmer.stem("constables"));
        Assert.assertEquals("constance", stemmer.stem("constance"));
        Assert.assertEquals("constance", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep4() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, true, true, true, false, false);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consignment", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consistence", stemmer.stem("consistency"));
        Assert.assertEquals("consistent", stemmer.stem("consistent"));
        Assert.assertEquals("consistent", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consolate", stemmer.stem("consolation"));
        Assert.assertEquals("consolate", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("console", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("console", stemmer.stem("consoles"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidate"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidated"));
        Assert.assertEquals("consolidate", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("consonant", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicuou", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicuous", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspirate", stemmer.stem("conspirator"));
        Assert.assertEquals("conspirate", stemmer.stem("conspirators"));
        Assert.assertEquals("conspire", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constable", stemmer.stem("constable"));
        Assert.assertEquals("constable", stemmer.stem("constables"));
        Assert.assertEquals("constance", stemmer.stem("constance"));
        Assert.assertEquals("constance", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep5() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, true, true, true, true, false);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consign", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consist", stemmer.stem("consistency"));
        Assert.assertEquals("consist", stemmer.stem("consistent"));
        Assert.assertEquals("consist", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consol", stemmer.stem("consolation"));
        Assert.assertEquals("consol", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("console", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("console", stemmer.stem("consoles"));
        Assert.assertEquals("consolid", stemmer.stem("consolidate"));
        Assert.assertEquals("consolid", stemmer.stem("consolidated"));
        Assert.assertEquals("consolid", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("conson", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspir", stemmer.stem("conspirator"));
        Assert.assertEquals("conspir", stemmer.stem("conspirators"));
        Assert.assertEquals("conspire", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constable", stemmer.stem("constable"));
        Assert.assertEquals("constable", stemmer.stem("constables"));
        Assert.assertEquals("constance", stemmer.stem("constance"));
        Assert.assertEquals("constance", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }

    @Test
    public void testStep6() throws IOException {
        FlexiblePorterStemmer stemmer = new FlexiblePorterStemmer(true, true, true, true, true, true);
        Assert.assertEquals("consign", stemmer.stem("consign"));
        Assert.assertEquals("consign", stemmer.stem("consigned"));
        Assert.assertEquals("consign", stemmer.stem("consigning"));
        Assert.assertEquals("consign", stemmer.stem("consignment"));
        Assert.assertEquals("consist", stemmer.stem("consist"));
        Assert.assertEquals("consist", stemmer.stem("consisted"));
        Assert.assertEquals("consist", stemmer.stem("consistency"));
        Assert.assertEquals("consist", stemmer.stem("consistent"));
        Assert.assertEquals("consist", stemmer.stem("consistently"));
        Assert.assertEquals("consist", stemmer.stem("consisting"));
        Assert.assertEquals("consist", stemmer.stem("consists"));
        Assert.assertEquals("consol", stemmer.stem("consolation"));
        Assert.assertEquals("consol", stemmer.stem("consolations"));
        Assert.assertEquals("consolatori", stemmer.stem("consolatory"));
        Assert.assertEquals("consol", stemmer.stem("console"));
        Assert.assertEquals("consol", stemmer.stem("consoled"));
        Assert.assertEquals("consol", stemmer.stem("consoles"));
        Assert.assertEquals("consolid", stemmer.stem("consolidate"));
        Assert.assertEquals("consolid", stemmer.stem("consolidated"));
        Assert.assertEquals("consolid", stemmer.stem("consolidating"));
        Assert.assertEquals("consol", stemmer.stem("consoling"));
        Assert.assertEquals("consolingli", stemmer.stem("consolingly"));
        Assert.assertEquals("consol", stemmer.stem("consols"));
        Assert.assertEquals("conson", stemmer.stem("consonant"));
        Assert.assertEquals("consort", stemmer.stem("consort"));
        Assert.assertEquals("consort", stemmer.stem("consorted"));
        Assert.assertEquals("consort", stemmer.stem("consorting"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuous"));
        Assert.assertEquals("conspicu", stemmer.stem("conspicuously"));
        Assert.assertEquals("conspiraci", stemmer.stem("conspiracy"));
        Assert.assertEquals("conspir", stemmer.stem("conspirator"));
        Assert.assertEquals("conspir", stemmer.stem("conspirators"));
        Assert.assertEquals("conspir", stemmer.stem("conspire"));
        Assert.assertEquals("conspir", stemmer.stem("conspired"));
        Assert.assertEquals("conspir", stemmer.stem("conspiring"));
        Assert.assertEquals("constabl", stemmer.stem("constable"));
        Assert.assertEquals("constabl", stemmer.stem("constables"));
        Assert.assertEquals("constanc", stemmer.stem("constance"));
        Assert.assertEquals("constanc", stemmer.stem("constancy"));
        Assert.assertEquals("constant", stemmer.stem("constant"));
        Assert.assertEquals("knack", stemmer.stem("knack"));
        Assert.assertEquals("knackeri", stemmer.stem("knackeries"));
        Assert.assertEquals("knack", stemmer.stem("knacks"));
        Assert.assertEquals("knag", stemmer.stem("knag"));
        Assert.assertEquals("knave", stemmer.stem("knave"));
        Assert.assertEquals("knave", stemmer.stem("knaves"));
        Assert.assertEquals("knavish", stemmer.stem("knavish"));
        Assert.assertEquals("knead", stemmer.stem("kneaded"));
        Assert.assertEquals("knead", stemmer.stem("kneading"));
        Assert.assertEquals("knee", stemmer.stem("knee"));
        Assert.assertEquals("kneel", stemmer.stem("kneel"));
        Assert.assertEquals("kneel", stemmer.stem("kneeled"));
        Assert.assertEquals("kneel", stemmer.stem("kneeling"));
        Assert.assertEquals("kneel", stemmer.stem("kneels"));
        Assert.assertEquals("knee", stemmer.stem("knees"));
        Assert.assertEquals("knell", stemmer.stem("knell"));
        Assert.assertEquals("knelt", stemmer.stem("knelt"));
        Assert.assertEquals("knew", stemmer.stem("knew"));
        Assert.assertEquals("knick", stemmer.stem("knick"));
        Assert.assertEquals("knif", stemmer.stem("knif"));
        Assert.assertEquals("knife", stemmer.stem("knife"));
        Assert.assertEquals("knight", stemmer.stem("knight"));
        Assert.assertEquals("knightli", stemmer.stem("knightly"));
        Assert.assertEquals("knight", stemmer.stem("knights"));
        Assert.assertEquals("knit", stemmer.stem("knit"));
        Assert.assertEquals("knit", stemmer.stem("knits"));
        Assert.assertEquals("knit", stemmer.stem("knitted"));
        Assert.assertEquals("knit", stemmer.stem("knitting"));
        Assert.assertEquals("knive", stemmer.stem("knives"));
        Assert.assertEquals("knob", stemmer.stem("knob"));
        Assert.assertEquals("knob", stemmer.stem("knobs"));
        Assert.assertEquals("knock", stemmer.stem("knock"));
        Assert.assertEquals("knock", stemmer.stem("knocked"));
        Assert.assertEquals("knocker", stemmer.stem("knocker"));
        Assert.assertEquals("knocker", stemmer.stem("knockers"));
        Assert.assertEquals("knock", stemmer.stem("knocking"));
        Assert.assertEquals("knock", stemmer.stem("knocks"));
        Assert.assertEquals("knopp", stemmer.stem("knopp"));
        Assert.assertEquals("knot", stemmer.stem("knot"));
        Assert.assertEquals("knot", stemmer.stem("knots"));
    }
}
