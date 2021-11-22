<script>
    import Output from "./Output.svelte";

    export let response = [];
    let data = {
        'distance-travelled-less-than': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/distance-travelled-searcher?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        response = [...response, ...await answer.json()];

        console.log(response);
    }
</script>

<main>
    <label>Distance Travelled Less Than<input type="number" name="distance-travelled-less-than" min="0"
                                    bind:value={data["distance-travelled-less-than"]}></label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
