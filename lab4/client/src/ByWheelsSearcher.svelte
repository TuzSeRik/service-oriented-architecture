<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'from': 0,
        'to': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8081/search/by-number-of-wheels/?"
            + "to=" + data["to"],
            {
                method: 'GET',
                mode: 'no-cors'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Wheels Number From<input type="number" name="from" min="0"
                                     bind:value={data["from"]}></label>
    <label>Wheels Number To<input type="number" name="to" min="0"
                                     bind:value={data["to"]}></label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>

</style>
