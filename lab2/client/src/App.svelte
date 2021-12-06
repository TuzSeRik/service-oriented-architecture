<script>
import BasicPostInput from "./BasicPostInput.svelte";
import BasicGetInput from "./BasicGetInput.svelte";
import BasicPutInput from "./BasicPutInput.svelte";
import BasicDeleteInput from "./BasicDeleteInput.svelte";
import DistanceCalculatorInput from "./DistanceCalculatorInput.svelte";
import DistanceSearcherInput from "./DistanceSearcherInput.svelte";
import WheelsSearcherInput from "./WheelsSearcherInput.svelte";

let form = 0;
</script>

<main>
    <h1>Welcome to 1st lab work!</h1>

    <table>
        <tr>
            <th>Basic Operations</th>
            <th>Additional Operations</th>
        </tr>
        <tr>
            <td>
                <table>
                    <tr>
                        <td><input type="radio" bind:group={form} name="form" value={1}/>POST</td>
                        <td><input type="radio" bind:group={form} name="form" value={2}/>GET</td>
                        <td><input type="radio" bind:group={form} name="form" value={3}/>PUT</td>
                        <td><input type="radio" bind:group={form} name="form" value={4}/>DELETE</td>
                    </tr>
                </table>
            </td>
            <td>
                <table>
                    <tr>
                        <td><input type="radio" bind:group={form} name="form" value={5}/>Distance Calculator</td>
                        <td><input type="radio" bind:group={form} name="form" value={6}/>Distance Searcher</td>
                        <td><input type="radio" bind:group={form} name="form" value={7}/>Wheels Searcher</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

    {#if form === 1}
        <BasicPostInput />
    {:else if (form === 2)}
        <BasicGetInput />
    {:else if (form === 3)}
        <BasicPutInput />
    {:else if (form === 4)}
        <BasicDeleteInput />
    {:else if (form === 5)}
        <DistanceCalculatorInput />
    {:else if (form === 6)}
        <DistanceSearcherInput />
    {:else if (form === 7)}
        <WheelsSearcherInput />
    {/if}
</main>

<style>

</style>
