<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'fuelType': 'ALCOHOL'
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8081/search/by-type/"
            + data["fuelType"],
            {
                method: 'GET'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>
        Fuel Type
        <select name="fuel-type" bind:value={data["fuelType"]}>
            <option value="ALCOHOL">Alcohol</option>
            <option value="MANPOWER">Manpower</option>
            <option value="NUCLEAR">Nuclear</option>
        </select>
    </label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>

</style>
