You have a text file in CSV format, with each row of the CSV file containing 4 columns:

1. City name
2. Country name
3. URL on the web to an image for this city
4. Description of the city

For example, the file might have name:   cities.txt

and contents:

> "Tokyo","Japan","http://media-cdn.tripadvisor.com/media/photo-s/03/9b/2e/18/tokyo.jpg","Tradition collides with pop culture in Tokyo, where you can reverently wander ancient temples before rocking out at a karaoke bar. Wake up before the sun to catch the lively fish auction at the Tsukiji Market, then refresh with a walk beneath the cherry blossom trees that line the Sumida River."
> "Sydney","Australia","http://media-cdn.tripadvisor.com/media/photo-s/03/9b/2e/15/sydney.jpg","Sydney offers plenty of historical and contemporary Australian flavor. The marvelous Sydney Opera House looks like a great origami sailboat, floating peacefully in a harbor. Wander the narrow cobblestone streets of The Rocks and then take in a street performance on the Circular Quay before heading into the Museum of Contemporary Art."
> "Los Angeles","United States","http://media-cdn.tripadvisor.com/media/photo-s/06/c4/ea/60/hiking-the-hollywood.jpg","Hollywood beckons to tourists who long for a brush with Tinseltown glamor. Tour movie studios, slip your hands into the famous prints at the TCL Chinese Theatre, or indulge in a celeb sighting at one of LA’s white-hot nightspots. Spot your favorite star while shopping at The Grove, or forget all about the famous eye candy as you take in the iconic artwork at The Getty."

This Android app can be freely rotated between portrait and landscape mode.  It bundles the cities.txt file and read it into memory at app startup.  It renders a vertically scrolling list of each city, with each row in the list showing the city name, country name, and a snippet of the description text.  Upon tap of a row, the full screen transitions to a detail view for one individual city.

License
--------

    Copyright 2015 Phil Brown

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

Open Source Licenses
--------

This app makes use of [Picasso](https://github.com/square/picasso), which is licensed under Apache 2.0:

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.