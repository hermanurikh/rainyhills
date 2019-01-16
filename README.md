

<h1> Rainy Hills logical task.</h1>

<h2>Task description</h2>

* INPUT - array of numbers
* OUTPUT: a number, further called "volume"

Array describes profile of a surface. The holes will be filled with water.

For array [3, 2, 4, 1, 2] the volume will be 2 - a hole between 3 and 4, and a hole between 4 and 2.

For [4, 1, 1, 0, 2, 3] the volume will be 8 - a hole is between 4 and 3.

<h2>Installation</h2>

* run `gradlew build` to prepare artifact
* start main class `com.crxmarkets.rainyhills.RainyHillsApplication`

<h2>Implementation note</h2>

The task is implemented with visualisation if array.length is <= 12 and if input numbers are between 0 and 11.

<h2>Examples</h2>
<h3>Main page</h3>

![main](https://user-images.githubusercontent.com/8960532/51268424-261ffa80-19d1-11e9-8790-ffca2ecd8998.JPG)

<h3>Result with visualisation</h3>

![result_visualisation](https://user-images.githubusercontent.com/8960532/51268540-6c755980-19d1-11e9-9712-e14918b20f0c.JPG)

<h3>Result without visualisation</h3>

![result_no_visualisation](https://user-images.githubusercontent.com/8960532/51268688-c24a0180-19d1-11e9-9822-af075521d437.JPG)
