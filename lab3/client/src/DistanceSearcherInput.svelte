<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'distance': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:888/distance-travelled-searcher?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Distance Travelled Less Than<input type="number" name="distance-travelled-less-than" min="0"
                                    bind:value={data["distance"]}></label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
