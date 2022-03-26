<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'number': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:7443/wheels-number-searcher?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Wheels Number Equal<input type="number" name="wheels-number-equal" min="0"
                                    bind:value={data["number"]}></label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
